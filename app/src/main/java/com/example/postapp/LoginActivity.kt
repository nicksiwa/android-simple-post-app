package com.example.postapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var mUsernameEditText: EditText
    private lateinit var mPasswordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUsernameEditText = findViewById(R.id.username_edit_text)
        mPasswordEditText = findViewById(R.id.password_edit_text)
    }

    fun onClickLoginButton(view: View) {
        if (mUsernameEditText.text.toString() == "admin" && mPasswordEditText.text.toString() == "admin") {
            AppPreferences.isLogin = true

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }
}