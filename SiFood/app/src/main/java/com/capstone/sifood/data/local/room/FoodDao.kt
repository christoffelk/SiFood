package com.capstone.sifood.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.local.entities.Food

@Dao
interface FoodDao {

    @Insert
    fun  insertFood(data: Food)

    @Query("SELECT * FROM food")
    fun getAllFood(): List<Food>

    @Query("SELECT * FROM article")
    fun getAllArticle() : LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(data : List<Article>)

    @Query("DELETE FROM food WHERE id = :id")
    fun deleteFood(id: String)
}