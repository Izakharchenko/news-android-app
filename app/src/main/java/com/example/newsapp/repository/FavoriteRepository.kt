package com.example.newsapp.repository

import com.example.newsapp.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun insert(news: Favorite)

    suspend fun getFavoriteById(id: Int): Favorite?

    suspend fun deleteById(news: Favorite)

    fun getFavorites(): Flow<List<Favorite>>
}