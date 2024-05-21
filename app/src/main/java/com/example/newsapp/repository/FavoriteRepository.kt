package com.example.newsapp.repository

import com.example.newsapp.db.FavoriteDao
import com.example.newsapp.model.Favorite

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    suspend fun insert(news: Favorite) {
        favoriteDao.insert(news)
    }

    suspend fun getNewsById(id: Int): Favorite? {
        return favoriteDao.getFavoriteById(id)
    }

    suspend fun deleteById(news: Favorite) {
        favoriteDao.delete(news)
    }


}