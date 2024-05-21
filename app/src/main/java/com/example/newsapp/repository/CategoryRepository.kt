package com.example.newsapp.repository

import com.example.newsapp.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>

}