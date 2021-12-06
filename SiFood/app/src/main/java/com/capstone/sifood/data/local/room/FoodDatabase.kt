package com.capstone.sifood.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.sifood.data.entities.Food


@Database(entities = [Food::class],
version = 1,
exportSchema = false)
abstract class FoodDatabase: RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {

        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getInstance(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    FoodDatabase::class.java, "food.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}