package com.example.postapp.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val baseUrl = "https://jsonplaceholder.typicode.com"
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    fun<T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
