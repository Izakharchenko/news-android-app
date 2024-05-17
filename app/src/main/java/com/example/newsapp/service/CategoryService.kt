package com.example.newsapp.service

import com.example.newsapp.model.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CategoryService {
    @Headers("Content-Type: application/json")
    @GET("categories")
    fun getCategories(): Call<List<Category>>

    @Headers("Content-Type: application/json")
    @GET("categories{id}")
    fun getCategory(@Path("id") id: Int): Call<Category>
}