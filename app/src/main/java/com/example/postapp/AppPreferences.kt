package com.example.postapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "PostApp"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val IS_LOGIN = Pair<String, Boolean>("is_login", false)

    fun init(contenx: Context) {
        preferences = contenx.getSharedPreferences(NAME, MODE)
    }

    fun redirect() {}

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var isLogin: Boolean
        get() = preferences.getBoolean(IS_LOGIN.first, IS_LOGIN.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_LOGIN.first, value)
        }
}

class PostApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AppPreferences.init(this)
    }
}
