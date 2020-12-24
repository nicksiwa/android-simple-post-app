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

class EditPostActivity : AppCompatActivity() {
    private lateinit var postTitleEditText: EditText
    private lateinit var postBodyEditText: EditText
    private var postID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_form)

        val intent = intent.extras
        postID = intent?.getInt("id")
        postTitleEditText = findViewById(R.id.post_title_edit_text)
        postBodyEditText = findViewById(R.id.post_body_edit_text)
        postTitleEditText.setText(intent?.get("title").toString())
        postBodyEditText.setText(intent?.get("body").toString())
    }

    fun onClickSave(view: View) {
        val post = Post(postID, postTitleEditText.text.toString(), postBodyEditText.text.toString())
        editPost(post)
    }

    private fun editPost(post: Post) {
        val request = ServiceBuilder.buildService(IService::class.java)
        val call = request.editPost(postID!!, post)

        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val intent = Intent(this@EditPostActivity, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@EditPostActivity, "Save successful", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@EditPostActivity, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}