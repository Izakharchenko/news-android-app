package com.example.newsapp.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Favorite
import com.example.newsapp.model.News
import com.example.newsapp.repository.FavoriteRepositoryImpl
import com.example.newsapp.repository.NewsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel(private val repository: NewsRepositoryImpl, private val repositoryFav: FavoriteRepositoryImpl) : ViewModel() {
    private val _article = MutableLiveData<News>()
    private val _viewCount = MutableLiveData<Int>()
    val article: LiveData<News> get() = _article
    val viewCount: LiveData<Int> get() = _viewCount

    fun fetchArticle(newsId: String) {
        viewModelScope.launch {
            try {
                val favorites  =  withContext(Dispatchers.IO) {repositoryFav.getFavoriteById(newsId.toInt())}
                if (favorites != null) {
                    _article.postValue(favorites!!)
                } else {
                    val result = repository.getNewsById(newsId.toInt())
                    _article.postValue(result)
                }

            } catch (e: Exception) {
                Log.e("ArticleViewModel", "Error fetching news", e)
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
             repositoryFav.getFavoriteById(id) != null
        }
    }
    fun incrementViewCount(id: Int) {
        viewModelScope.launch {
            try {
                val newViewCount = repository.incrementViewCount(id)
                _viewCount.postValue(newViewCount)
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

}