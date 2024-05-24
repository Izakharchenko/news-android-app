package com.example.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Category
import com.example.newsapp.model.News
import com.example.newsapp.repository.CategoryRepositoryImpl
import com.example.newsapp.repository.NewsRepositoryImpl
import com.example.newsapp.service.CategoryService
import com.example.newsapp.service.NewsService
import com.example.newsapp.service.ServiceCreator
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = NewsRepositoryImpl(ServiceCreator.create<NewsService>())
    private val categoryRepository = CategoryRepositoryImpl(ServiceCreator.create<CategoryService>())

    private val _news = MutableLiveData<List<News>>()
    private val _categories = MutableLiveData<List<Category>>()

    val news: LiveData<List<News>> get() = _news
    val categories: LiveData<List<Category>> get() = _categories

    private var allNews = listOf<News>()
    init {
        loadCategories()
        fetchNews()
    }
    fun fetchNews() {
        viewModelScope.launch {
            try {
                val result = repository.getNews()
                allNews = result
                _news.postValue(result)
            } catch (e: Exception) {
                _news.postValue(emptyList())
            }
        }
    }

    fun refreshFavoriteNews() {
        loadCategories()
        fetchNews()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val categoryList = categoryRepository.getCategories()
            _categories.value = listOf(Category(0, "All Categories")) + categoryList
        }
    }

    fun filterNewsByCategory(category: Int) {
        viewModelScope.launch {
            if (category == 0) {
                _news.postValue(allNews)
            } else {
                _news.postValue(repository.getNewsByCategory(category))
            }
        }
    }
}