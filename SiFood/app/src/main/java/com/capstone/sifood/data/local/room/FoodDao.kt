package com.capstone.sifood.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.capstone.sifood.data.entities.Food

@Dao
interface FoodDao {

    @Insert
    suspend fun  insertFood(data: Food)

    @Query("SELECT * FROM food")
    suspend fun getAllFood(): List<Food>

    @Query("DELETE FROM food WHERE id = :id")
    suspend fun deleteFood(id: String)
}