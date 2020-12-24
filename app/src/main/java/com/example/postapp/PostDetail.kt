package com.example.postapp

import android.content.Intent
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
    private lateinit var postViewTitle: TextView
    private lateinit var postViewBody: TextView
    private lateinit var loader: ProgressBar
    private var postID: Int? = null
    private val request = ServiceBuilder.buildService(IService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        postViewTitle = findViewById(R.id.post_detail_title)
        postViewBody = findViewById(R.id.post_detail_body)
        loader = findViewById(R.id.post_detail_loader)
        val intent = intent.extras
        postID = intent?.get("postID").toString().toInt()

        getPostById(postID!!)
    }

    private fun getPostById(postId: Int) {
        val call = request.getPost(postId)

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

    private fun deletePost(postId: Int) {
        val call = request.deletePost(postId)

        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val intent = Intent(this@PostDetail, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@PostDetail, "Delete Successful", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@PostDetail, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun clickEditPost(view: View) {
        val intent = Intent(this, EditPostActivity::class.java)
        intent.putExtra("id", postID)
        intent.putExtra("title", postViewTitle.text)
        intent.putExtra("body", postViewBody.text)
        startActivity(intent)
    }

    fun clickDeletePost(view: View) {
        deletePost(postID!!)
    }
}