package com.capstone.sifood.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.capstone.sifood.data.local.entities.Food

@Dao
interface FoodDao {

    @Insert
    fun  insertFood(data: Food)

    @Query("SELECT * FROM food")
    fun getAllFood(): List<Food>

    @Query("DELETE FROM food WHERE id = :id")
    fun deleteFood(id: String)
}