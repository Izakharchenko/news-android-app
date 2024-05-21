package com.example.newsapp.repository

import com.example.newsapp.model.News

interface NewsRepository {
    suspend fun getNews(): List<News>
    suspend fun getNewsById(id: Int): News

    suspend fun getNewsByCategory(category: Int): List<News>
}