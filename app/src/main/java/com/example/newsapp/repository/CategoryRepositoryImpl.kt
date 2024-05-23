package com.example.newsapp.repository

import android.util.Log
import com.example.newsapp.model.Category
import com.example.newsapp.service.CategoryService
import com.example.newsapp.service.ServiceCreator
import com.example.newsapp.service.ServiceCreator.await

class CategoryRepositoryImpl(private val categoryService: CategoryService): CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return try {
            categoryService.getCategories().await() ?: emptyList()
        } catch (e: Exception) {
            Log.e("categoryService", e.message.toString())
            emptyList()
        }
    }

}