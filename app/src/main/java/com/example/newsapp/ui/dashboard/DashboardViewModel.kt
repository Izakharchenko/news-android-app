package com.example.newsapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.PopArticle
import com.example.newsapp.repository.CategoryRepositoryImpl
import com.example.newsapp.repository.NewsRepositoryImpl
import com.example.newsapp.service.CategoryService
import com.example.newsapp.service.NewsService
import com.example.newsapp.service.ServiceCreator
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val newsRepository = NewsRepositoryImpl(ServiceCreator.create<NewsService>())
    private val categoryRepository = CategoryRepositoryImpl(ServiceCreator.create<CategoryService>())

    private val _totalNewsCount = MutableLiveData<Int>()
    private val _totalCategoryCount = MutableLiveData<Int>()
    private val _popArticle = MutableLiveData<PopArticle>()
    val totalNewsCount: LiveData<Int> get() = _totalNewsCount
    val totalCategoryCount: LiveData<Int> get() = _totalCategoryCount
    val popArticle: LiveData<PopArticle> get() = _popArticle

    init {
        fetchDashboardStats()
    }
    private fun fetchDashboardStats() {
        viewModelScope.launch {
            _totalNewsCount.value = newsRepository.getNews().size
            _totalCategoryCount.value = categoryRepository.getCategories().size
            _popArticle.value = newsRepository.getMostPopularNews()
        }
    }

    fun refreshDashboardStats() {
        fetchDashboardStats()
    }
}