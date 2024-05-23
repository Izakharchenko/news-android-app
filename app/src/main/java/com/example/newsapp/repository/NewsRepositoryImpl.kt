package com.example.newsapp.repository

import android.app.Application
import android.util.Log
import com.example.newsapp.model.News
import com.example.newsapp.model.PopArticle
import com.example.newsapp.service.NewsService
import com.example.newsapp.service.ServiceCreator
import com.example.newsapp.service.ServiceCreator.await

class NewsRepositoryImpl() : NewsRepository {

    override suspend fun getNews(): List<News> {
        return try {
            ServiceCreator.create<NewsService>().getPosts().await() ?: emptyList()
        } catch (e: Exception) {
            Log.e("getNews", e.message.toString())
            emptyList()
        }
    }

    override suspend fun getNewsById(id: Int): News {
        return try {
            ServiceCreator.create<NewsService>().getPost(id).await() ?: defaultArticle()
        } catch (e: Exception) {
            Log.e("getNewsById", e.message.toString())
            defaultArticle()
        }
    }

    override suspend fun getNewsByCategory(category: Int): List<News> {
        return try {
            ServiceCreator.create<NewsService>().getPostsByCategories(category).await()
        } catch (e: Exception) {
            Log.e("getNewsByCategory", e.message.toString())
            emptyList()
        }
    }

    override suspend fun incrementViewCount(id: Int): Int {
        return try {
            ServiceCreator.create<NewsService>().incrementViewCount(id).await() ?: -1
        } catch (e: Exception) {
            Log.e("incrementViewCount", e.message.toString())
            -1
        }

    }
    suspend fun getMostPopularNews(): PopArticle {
        return  try {
            ServiceCreator.create<NewsService>().getMostPopularNews().await() ?: defaultPopArticle()
        } catch (e: Exception) {
            Log.e("getMostPopularNews", e.message.toString())
            defaultPopArticle()
        }
    }

    private fun defaultPopArticle(): PopArticle {
        return PopArticle(
            title = "No data",
            views= 0
        )
    }
    private fun defaultArticle(): News {
        return News(
            id = -1, title = "No data", categoryId = -1, categoryTitle = "No data",
            body = "No data", cover = "No data", author = "No data", source = "No data", views = 0
        )
    }


}