package com.example.newsapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Favorite
import com.example.newsapp.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel( private val repository: FavoriteRepository) : ViewModel() {

    val favoriteNews: LiveData<List<Favorite>> = repository.getFavorites().asLiveData()
}