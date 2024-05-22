package com.example.newsapp.service

import com.example.newsapp.model.News
import com.example.newsapp.model.PopArticle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NewsService {
    @Headers("Content-Type: application/json")
    @GET("news")
    fun getPosts(): Call<List<News>>

    @Headers("Content-Type: application/json")
    @GET("news/{id}")
    fun getPost(@Path("id") id: Int): Call<News>

    @Headers("Content-Type: application/json")
    @GET("news/categories/{id}")
    fun getPostsByCategories(@Path("id") id: Int): Call<List<News>>

    @Headers("Content-Type: application/json")
    @PATCH("news/{id}/increment-view-count")
    fun incrementViewCount(@Path("id") id: Int) : Call<Int>

    @Headers("Content-Type: application/json")
    @GET("news/pop")
    fun getMostPopularNews(): Call<PopArticle>
}