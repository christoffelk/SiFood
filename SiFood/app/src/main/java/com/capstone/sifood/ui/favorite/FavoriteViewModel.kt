package com.capstone.sifood.ui.favorite

import androidx.lifecycle.ViewModel
import com.capstone.sifood.data.Repository

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    fun getFavorite() = repository.getFavoriteFood()
}