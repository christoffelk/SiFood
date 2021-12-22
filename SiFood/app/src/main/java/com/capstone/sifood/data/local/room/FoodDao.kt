package com.capstone.sifood.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.Food2

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun  insertFood(data: List<Food>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoodLocation(data: List<Food2>)
    @Query("SELECT * FROM food")
    fun getAllFood(): LiveData<List<Food>>

    @Query("SELECT * FROM foodlocation where provinceEng = :location or province = :location")
    fun getLocationFood(location:String)  : LiveData<List<Food2>>
    @Query("SELECT * FROM article")
    fun getAllArticle() : LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(data : List<Article>)

    @Query("SELECT EXISTS(SELECT * FROM food where id = :id)")
    fun checkFood(id: String) : Boolean

    @Query("DELETE FROM food WHERE id = :id")
    fun deleteFood(id: String)
}