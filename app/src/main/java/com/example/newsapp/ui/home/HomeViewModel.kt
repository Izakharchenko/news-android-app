package com.example.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Category
import com.example.newsapp.model.News
import com.example.newsapp.repository.CategoryRepositoryImpl
import com.example.newsapp.repository.NewsRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = NewsRepositoryImpl()
    private val categoryRepository = CategoryRepositoryImpl()

    private val _news = MutableLiveData<List<News>>()
    private val _categories = MutableLiveData<List<Category>>()

    val news: LiveData<List<News>> get() = _news
    val categories: LiveData<List<Category>> get() = _categories

    fun fetchNews() {
        viewModelScope.launch {
            try {
                val result = repository.getNews()
                _news.postValue(result)
            } catch (e: Exception) {
                // Обробка помилок
                _news.postValue(emptyList())
            }
        }
    }

    fun refreshFavoriteNews() {
        viewModelScope.launch {
        }
    }

}