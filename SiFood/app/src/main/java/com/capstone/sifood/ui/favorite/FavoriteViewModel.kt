package com.capstone.sifood.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.sifood.data.Repository
import com.capstone.sifood.data.local.entities.Food

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    fun getFavorite() = repository.getFavoriteFood()
}