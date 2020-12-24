package com.example.postapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.postapp.model.Post
import com.example.postapp.service.IService
import com.example.postapp.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePostActivity : AppCompatActivity() {
    private lateinit var postTitleEditText: EditText
    private lateinit var postBodyEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_form)

        postTitleEditText = findViewById(R.id.post_title_edit_text)
        postBodyEditText = findViewById(R.id.post_body_edit_text)
    }

    fun onClickSave(view: View) {
        val post = Post(
            title = postTitleEditText.text.toString(),
            body = postBodyEditText.text.toString()
        )

        createPost(post)
    }

    private fun createPost(post: Post) {
        val request = ServiceBuilder.buildService(IService::class.java)
        val call = request.addPost(post)

        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val intent = Intent(this@CreatePostActivity, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@CreatePostActivity, "Create Successful", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@CreatePostActivity, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}