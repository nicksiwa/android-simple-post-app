package com.example.postapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postapp.adapter.PostsAdapter
import com.example.postapp.model.Post
import com.example.postapp.service.IService
import com.example.postapp.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loader = findViewById<ProgressBar>(R.id.post_list_loader)
        val request = ServiceBuilder.buildService(IService::class.java)
        val call = request.getAllPosts()
        val postsView = findViewById<RecyclerView>(R.id.post_list)

        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    postsView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = PostsAdapter(response.body()!!, this@MainActivity)
                        loader.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
