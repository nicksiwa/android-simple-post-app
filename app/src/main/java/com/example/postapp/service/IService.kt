package com.example.postapp.service

import com.example.postapp.model.Post
import retrofit2.Call
import retrofit2.http.*

interface IService {
    @GET("posts")
    fun getAllPosts(): Call<List<Post>>

    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int): Call<Post>

    @POST("posts")
    fun addPost(@Body post: Post): Call<Post>

    @PUT("posts/{id}")
    fun editPost(@Path("id") id: Int, @Body post: Post): Call<Post>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<Post>
}
