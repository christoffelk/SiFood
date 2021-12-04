package com.capstone.sifood.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.capstone.sifood.data.entities.Food
import java.util.concurrent.Executors


@Database(entities = [Food::class],
version = 1,
exportSchema = false)
abstract class FoodDatabase: RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {

        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getInstance(context: Context): FoodDatabase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    FoodDatabase::class.java, "food.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}