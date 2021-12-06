package com.capstone.sifood.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.sifood.data.firebase.database.FirebaseDatabase
import com.capstone.sifood.data.local.entities.Food
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {
    private val _home = MutableLiveData<List<Food>>()
    val home : LiveData<List<Food>> = _home

    private val firebaseDatabase = FirebaseDatabase()

    init {
        _home.value = firebaseDatabase.getPopularFood()
    }

}