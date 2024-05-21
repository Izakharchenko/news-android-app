package com.example.newsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "category_title") val categoryTitle: String,
    val title: String,
    val cover: String,
    val body: String,
    val source: String,
    val author: String,
)
