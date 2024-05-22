package com.example.newsapp.repository

import com.example.newsapp.db.FavoriteDao
import com.example.newsapp.model.Favorite
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(private val favoriteDao: FavoriteDao) : FavoriteRepository {
    override suspend fun insert(news: Favorite) {
        favoriteDao.insert(news)
    }

    override suspend fun getNewsById(id: Int): Favorite? {
        return favoriteDao.getFavoriteById(id)
    }

    override suspend fun deleteById(news: Favorite) {
        favoriteDao.delete(news)
    }

    override fun getFavorites(): Flow<List<Favorite>> {
        return favoriteDao.getFavorites()
    }


}