package com.capstone.sifood.ui.foodDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.sifood.data.Repository
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.FoodFavorite
import com.capstone.sifood.data.local.entities.FoodLocation

class FoodDetailViewModel(private val repository: Repository) : ViewModel() {

    private val _foodPopular = MutableLiveData<Food>()
    val foodPopular: LiveData<Food> = _foodPopular

    private val _foodLocation = MutableLiveData<FoodLocation>()
    val foodLocation: LiveData<FoodLocation> = _foodLocation

    fun setDataPopular(data: Food){
        _foodPopular.value = data
    }

    fun setDataLocation(data: FoodLocation){
        _foodLocation.value = data
    }

    fun check(id: String): Boolean {
        return repository.checkFood(id)
    }

    fun insert(food: FoodFavorite) {
        repository.insertFavorite(food)
    }

    fun delete(data: FoodFavorite){
        repository.deleteFavorite(data)
    }
}