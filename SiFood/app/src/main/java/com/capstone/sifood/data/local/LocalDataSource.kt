package com.capstone.sifood.data.local

import androidx.lifecycle.LiveData
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.FoodFavorite
import com.capstone.sifood.data.local.entities.FoodLocation
import com.capstone.sifood.data.local.room.FoodDao

class LocalDataSource private constructor(private val mFoodDao: FoodDao) {

    companion object{
        @Volatile
        var INSTANCE: LocalDataSource? = null
        fun getInstance(foodDao: FoodDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(foodDao).apply {
                INSTANCE = this
            }
    }
    fun getArticle() : LiveData<List<Article>> = mFoodDao.getAllArticle()
    fun insertArticle(article : List<Article>) = mFoodDao.insertArticle(article)
    fun getAllFood(): LiveData<List<Food>> = mFoodDao.getAllFood()
    fun getLocation(location:String) = mFoodDao.getLocationFood(location)
    fun insertFood(data: List<Food>) = mFoodDao.insertFood(data)
    fun getFavorite() = mFoodDao.getFavorite()
    fun insertFoodLocation(data: List<FoodLocation>) = mFoodDao.insertFoodLocation(data)
    fun insertFoodFavorite(data: FoodFavorite) = mFoodDao.insertFavorite(data)
    fun checkFood(id: String) : Boolean = mFoodDao.checkFood(id)
    fun deleteFood(id: String) = mFoodDao.deleteFood(id)
    fun deleteFavorite(data: FoodFavorite) = mFoodDao.deleteFavorite(data)
    fun insertAllFoodFavorite(data: List<FoodFavorite>) = mFoodDao.insertAllFavorite(data)
    fun deleteallFavorite() = mFoodDao.deleteAllFavorite()

}