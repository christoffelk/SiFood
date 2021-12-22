package com.capstone.sifood.ui.foodDetail

import androidx.lifecycle.ViewModel
import com.capstone.sifood.data.Repository
import com.capstone.sifood.data.local.entities.Food

class FoodDetailViewModel(private val repository: Repository) : ViewModel() {
    fun check(id: String): Boolean {
        return repository.checkFood(id)
    }

    //fun insert(food: Food) {
      //  repository.insertFavoriteFood(food)
    //}

    fun delete(id: String) {
        repository.deleteFavoriteFood(id)
    }

    fun deleteFirebase(data: Food){
        repository.deleteFavoriteFirebase(data)
    }

    fun insertFirebase(data: Food){
        repository.insertFavoriteFirebase(data)
    }
}