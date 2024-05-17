package com.example.newsapp.model

import com.google.gson.annotations.SerializedName

data class News(
    val id: Int?,
    val title: String,
    @SerializedName("category_id") val categoryId: Int,
    val cover: String,
    val body: String,
    val source: String,
    val author: String,
)
