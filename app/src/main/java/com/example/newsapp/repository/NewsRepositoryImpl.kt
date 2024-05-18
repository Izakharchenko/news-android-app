package com.example.newsapp.repository

import com.example.newsapp.model.News
import com.example.newsapp.service.NewsService
import com.example.newsapp.service.ServiceCreator
import com.example.newsapp.service.ServiceCreator.await

class NewsRepositoryImpl : NewsRepository {
    override suspend fun getNews(): List<News> {
        return ServiceCreator.create<NewsService>().getPosts().await()
    }

    override suspend fun getNewsById(id: Int): News {
        return ServiceCreator.create<NewsService>().getPost(id).await()
    }
}