package com.capstone.sifood.ui.home

import androidx.lifecycle.ViewModel
import com.capstone.sifood.data.Repository
import com.capstone.sifood.other.Constant.LOCATION_NAME

class HomeViewModel(private val repository: Repository) : ViewModel() {
    fun getPopularFood() = repository.getPopularFood()
    fun getFoodByLocation() = repository.getFoodByLocation(LOCATION_NAME)
    fun getImageSlider() = repository.getImageSlider()
}