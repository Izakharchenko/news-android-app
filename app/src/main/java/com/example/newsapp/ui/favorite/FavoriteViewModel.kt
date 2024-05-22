package com.example.newsapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.newsapp.model.Favorite
import com.example.newsapp.repository.FavoriteRepositoryImpl

class FavoriteViewModel( private val repository: FavoriteRepositoryImpl) : ViewModel() {

    val favoriteNews: LiveData<List<Favorite>> = repository.getFavorites().asLiveData()
}