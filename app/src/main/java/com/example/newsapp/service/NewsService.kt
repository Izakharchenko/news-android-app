package com.example.newsapp.service

import com.example.newsapp.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface NewsService {
    @Headers("Content-Type: application/json")
    @GET("news")
    fun getPosts(): Call<List<News>>

    @Headers("Content-Type: application/json")
    @GET("news/{id}")
    fun getPost(@Path("id") id: Int): Call<News>
}