package com.example.postapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
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

        if (!AppPreferences.isLogin) {
            Toast.makeText(this, "Login False", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Login True", Toast.LENGTH_SHORT).show()
        }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_btn -> AppPreferences.isLogin = false
        }

        return super.onOptionsItemSelected(item)
    }

    fun onClickCreatePost(view: View) {
        val intent = Intent(this, CreatePostActivity::class.java)
        startActivity(intent)
    }
}
