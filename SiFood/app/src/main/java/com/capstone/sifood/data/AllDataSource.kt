package com.capstone.sifood.data

import androidx.lifecycle.LiveData
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.firebase.entities.Image
import com.capstone.sifood.data.firebase.entities.Resource
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.FoodFavorite
import com.capstone.sifood.data.local.entities.FoodLocation

interface AllDataSource {
    fun getPopularFood(): LiveData<Resource<List<Food>>>
    fun getFoodByLocation(location: String): LiveData<Resource<List<FoodLocation>>>
    fun getFavoriteFood(): LiveData<List<Food>>
    fun getImageSlider() : LiveData<List<Image>>
    fun deleteFavoriteFood(id: String)
    fun checkFood(id: String): Boolean
    fun getArticle(): LiveData<Resource<List<Article>>>
    fun getLastLocation(): LiveData<List<Double>>
    fun getLocationName(lat: Double, long: Double): LiveData<String>

    fun getFavoriteFromFirebase(uid: String): LiveData<Resource<List<FoodFavorite>>>

    fun insertFavorite(data: FoodFavorite)
    fun deleteFavorite(data: FoodFavorite)

}