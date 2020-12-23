package com.example.postapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.postapp.model.Post
import com.example.postapp.service.IService
import com.example.postapp.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val postViewTitle = findViewById<TextView>(R.id.post_detail_title)
        val postViewBody = findViewById<TextView>(R.id.post_detail_body)
        val loader = findViewById<ProgressBar>(R.id.post_detail_loader)
        val intent = intent.extras
        val postID: String = intent?.get("postID").toString()
        val request = ServiceBuilder.buildService(IService::class.java)
        val call = request.getPost(postID)

        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val post = response.body()!!
                postViewTitle.text = post.title
                postViewBody.text = post.body
                loader.visibility = View.GONE
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@PostDetail, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}