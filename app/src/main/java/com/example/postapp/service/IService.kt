package com.example.postapp.service

import com.example.postapp.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IService {
    @GET("/posts")
    fun getAllPosts(): Call<List<Post>>

    @GET("/posts/{id}")
    fun getPost(@Path("id") id: String): Call<Post>
}
