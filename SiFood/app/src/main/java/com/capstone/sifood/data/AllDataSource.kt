package com.capstone.sifood.data

import androidx.lifecycle.LiveData
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.firebase.entities.Image
import com.capstone.sifood.data.firebase.entities.Resource
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.Food2

interface AllDataSource {
    fun getPopularFood(): LiveData<Resource<List<Food>>>
    fun getFoodByLocation(location: String): LiveData<Resource<List<Food2>>>
    fun getFavoriteFood(): LiveData<List<Food>>
    fun getImageSlider() : LiveData<List<Image>>
    fun deleteFavoriteFood(id: String)
    fun checkFood(id: String): Boolean
    fun getArticle(): LiveData<Resource<List<Article>>>
    fun getLastLocation(): LiveData<List<Double>>
    fun getLocationName(lat: Double, long: Double): LiveData<String>

    fun insertFavoriteFirebase(data: Food)
    fun deleteFavoriteFirebase(data: Food)
    fun getFavoriteFromFirebase(uid: String): LiveData<List<Food>>
}