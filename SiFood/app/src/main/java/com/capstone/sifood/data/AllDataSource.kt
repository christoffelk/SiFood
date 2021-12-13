package com.capstone.sifood.data

import androidx.lifecycle.LiveData
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.firebase.entities.Image
import com.capstone.sifood.data.firebase.entities.Resource
import com.capstone.sifood.data.local.entities.Food

interface AllDataSource {
    fun getPopularFood(): LiveData<List<Food>>
    fun getFoodByLocation(location: String): LiveData<List<Food>>
    fun getFavoriteFood(): LiveData<List<Food>>
    fun insertFavoriteFood(data: Food)
    fun getImageSlider() : LiveData<List<Image>>
    fun deleteFavoriteFood(id: String)
    fun checkFood(id: String): Boolean
    fun getArticle(): LiveData<Resource<List<Article>>>
}