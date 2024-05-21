package com.example.newsapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.model.Favorite

@Dao
interface FavoriteDao {
    @Insert
    fun insert(news: Favorite)
    @Query("SELECT * FROM Favorite WHERE id = :id LIMIT 1")
    fun getFavoriteById(id: Int): Favorite?
    @Query("SELECT * FROM Favorite")
    fun getAll(): List<Favorite>

    @Delete
    fun delete(favorite: Favorite)
}