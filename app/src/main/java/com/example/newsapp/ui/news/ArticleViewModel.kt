package com.example.newsapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.News
import com.example.newsapp.repository.NewsRepositoryImpl
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private val repository = NewsRepositoryImpl()

    private val _article = MutableLiveData<News>()
    val article: LiveData<News> get() = _article

    fun fetchArticle(newsId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getNewsById(newsId.toInt())
                _article.postValue(result)
            } catch (e: Exception) {
                // Обробка помилок
//                _article.postValue()
            }
        }
    }
    fun getArticle(id: String): LiveData<News?> {
        return _article
    }

}