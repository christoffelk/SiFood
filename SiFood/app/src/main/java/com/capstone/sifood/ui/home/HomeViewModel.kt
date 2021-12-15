package com.capstone.sifood.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.capstone.sifood.data.Repository
import com.capstone.sifood.data.local.entities.Food

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _koordinat = MutableLiveData<List<Double>>()

    private val _locationName: LiveData<String> = _koordinat.switchMap {
        repository.getLocationName(it[0], it[1])
    }

    val listFood: LiveData<List<Food>> = _locationName.switchMap {
        repository.getFoodByLocation(it)
    }

    init {
        _koordinat.value = repository.getLastLocation().value
    }

    fun getPopularFood() = repository.getPopularFood()
    fun getFoodByLocation(location: String) = repository.getFoodByLocation(location)
    fun getImageSlider() = repository.getImageSlider()

    fun getLocationName(lat: Double, long:Double) = repository.getLocationName(lat, long)
    fun getLastLocation() = repository.getLastLocation()
}