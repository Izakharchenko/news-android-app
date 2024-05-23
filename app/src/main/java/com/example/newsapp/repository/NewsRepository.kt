package com.example.newsapp.repository

import com.example.newsapp.model.News
import com.example.newsapp.model.PopArticle

interface NewsRepository {
    suspend fun getNews(): List<News>
    suspend fun getNewsById(id: Int): News

    suspend fun getNewsByCategory(category: Int): List<News>

    suspend fun  incrementViewCount(id: Int) : Int

    suspend fun getMostPopularNews(): PopArticle
}