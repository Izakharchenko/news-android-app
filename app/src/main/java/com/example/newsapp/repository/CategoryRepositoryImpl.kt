package com.example.newsapp.repository

import com.example.newsapp.model.Category
import com.example.newsapp.service.CategoryService
import com.example.newsapp.service.NewsService
import com.example.newsapp.service.ServiceCreator
import com.example.newsapp.service.ServiceCreator.await

class CategoryRepositoryImpl: CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return ServiceCreator.create<CategoryService>().getCategories().await()
    }

}