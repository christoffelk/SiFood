package com.capstone.sifood.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.FoodFavorite
import com.capstone.sifood.data.local.entities.FoodLocation

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun  insertFood(data: List<Food>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoodLocation(data: List<FoodLocation>)

    @Query("SELECT * FROM food")
    fun getAllFood(): LiveData<List<Food>>

    @Query("SELECT * FROM foodlocation where provinceEng = :location or province = :location")
    fun getLocationFood(location:String)  : LiveData<List<FoodLocation>>

    @Query("SELECT * FROM foodFavorite")
    fun getFavorite() : LiveData<List<FoodFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(data : FoodFavorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFavorite(data : List<FoodFavorite>)

    @Delete
    fun deleteFavorite(data: FoodFavorite)

    @Query("SELECT * FROM article")
    fun getAllArticle() : LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(data : List<Article>)

    @Query("SELECT EXISTS(SELECT * FROM foodFavorite where id = :id)")
    fun checkFood(id: String) : Boolean

    @Query("DELETE FROM foodFavorite WHERE id = :id")
    fun deleteFood(id: String)

    @Query("DELETE FROM foodFavorite")
    fun deleteAllFavorite()
}