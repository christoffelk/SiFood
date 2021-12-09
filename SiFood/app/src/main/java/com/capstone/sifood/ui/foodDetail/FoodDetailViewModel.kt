package com.capstone.sifood.ui.foodDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.sifood.data.Repository
import com.capstone.sifood.data.local.entities.Food
import kotlinx.coroutines.launch

class FoodDetailViewModel(private val repository: Repository) : ViewModel() {
    private var _button = MutableLiveData<Boolean>()
    val button : LiveData<Boolean> = _button
    fun insert(food: Food) {
        _button.value = true
        repository.insertFavoriteFood(food)
    }
    fun delete(id: String) {
        _button.value = false
        repository.deleteFavoriteFood(id)
    }
}