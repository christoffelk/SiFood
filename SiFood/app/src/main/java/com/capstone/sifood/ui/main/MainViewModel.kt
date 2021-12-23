package com.capstone.sifood.ui.main

import androidx.lifecycle.ViewModel
import com.capstone.sifood.data.Repository

class MainViewModel(private val repository: Repository): ViewModel() {
    fun deleteAllFavorite() = repository.deleteAllFavorite()
}