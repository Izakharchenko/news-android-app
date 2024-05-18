package com.example.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.News
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.repository.NewsRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = NewsRepositoryImpl()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> get() = _news

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

}