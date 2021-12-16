package com.capstone.sifood.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.capstone.sifood.data.Repository
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.other.Constant.LATITUDE
import com.capstone.sifood.other.Constant.LOCATION_NAME
import com.capstone.sifood.other.Constant.LONGITUDE

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _koordinat = repository.getLastLocation()
    private val _locationName: LiveData<String> = _koordinat.switchMap {
        repository.getLocationName(it[0], it[1])
    }
    val listFood: LiveData<List<Food>> = _locationName.switchMap {
        repository.getFoodByLocation(it)
    }
    fun getPopularFood() = repository.getPopularFood()
    fun getImageSlider() = repository.getImageSlider()
}