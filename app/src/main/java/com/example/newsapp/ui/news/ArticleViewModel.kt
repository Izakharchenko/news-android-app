package com.example.newsapp.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.db.AppDatabase
import com.example.newsapp.model.Favorite
import com.example.newsapp.model.News
import com.example.newsapp.repository.FavoriteRepository
import com.example.newsapp.repository.NewsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel(private val repository: NewsRepositoryImpl, private val repositoryFav: FavoriteRepository) : ViewModel() {

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

    fun addNewsToFavorites(news: Favorite) {
        viewModelScope.launch(Dispatchers.IO){
            repositoryFav.insert(news)
        }
    }

    fun removeNewsFromFavorites(news: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryFav.deleteById(news)
        }
    }

    suspend fun isNewsFavorite(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
             repositoryFav.getNewsById(id) != null
        }
    }

}