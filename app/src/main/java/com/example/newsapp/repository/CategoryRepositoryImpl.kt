package com.example.newsapp.repository

import com.example.newsapp.model.Category
import com.example.newsapp.service.CategoryService
import com.example.newsapp.service.ServiceCreator
import com.example.newsapp.service.ServiceCreator.await

class CategoryRepositoryImpl(private val categoryService: CategoryService): CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return categoryService.getCategories().await()
    }

}