package com.capstone.sifood.data.local

import com.capstone.sifood.data.local.entities.Food
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

    fun getAllFood(): List<Food> = mFoodDao.getAllFood()
    fun insertFood(data: Food) = mFoodDao.insertFood(data)
    fun deleteFood(id: String) = mFoodDao.deleteFood(id)

}