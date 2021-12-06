package com.capstone.sifood.data

import androidx.lifecycle.LiveData
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.remote.response.ArticlesItem

interface AllDataSource {
    fun getPopularFood(): List<Food>
    fun getFoodByLocation(location: String): List<Food>
    fun getFavoriteFood():List<Food>
    fun insertFavoriteFood(data: Food)
    fun deleteFavoriteFood(id: String)
    fun getArticle(): LiveData<List<ArticlesItem>>
}