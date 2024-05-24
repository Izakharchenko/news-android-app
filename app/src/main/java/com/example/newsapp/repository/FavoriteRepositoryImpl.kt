package com.example.newsapp.repository

import com.example.newsapp.db.FavoriteDao
import com.example.newsapp.model.Favorite
import com.example.newsapp.model.News
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(private val favoriteDao: FavoriteDao) : FavoriteRepository {
    override suspend fun insert(news: Favorite) {
        favoriteDao.insert(news)
    }

    override suspend fun getFavoriteById(id: Int): News? {
        val favorite = favoriteDao.getFavoriteById(id)
        return favorite?.toNews()
    }

    override suspend fun deleteById(news: Favorite) {
        favoriteDao.delete(news)
    }

    override fun getFavorites(): Flow<List<Favorite>> {
        return favoriteDao.getFavorites()
    }


}