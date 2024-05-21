package com.example.newsapp.ui.news

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.FavoriteRepository
import com.example.newsapp.repository.NewsRepositoryImpl

class ArticleViewModelFactory(
    private val repository: NewsRepositoryImpl,
    private val repositoryFav: FavoriteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel(repository, repositoryFav) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}