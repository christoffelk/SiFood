package com.capstone.sifood.di

import android.content.Context
import com.capstone.sifood.data.Repository
import com.capstone.sifood.data.firebase.database.FirebaseDatabase
import com.capstone.sifood.data.local.LocalDataSource
import com.capstone.sifood.data.local.room.FoodDatabase
import com.capstone.sifood.data.remote.RemoteDataSource
import com.capstone.sifood.other.AppExecutors
import com.capstone.sifood.other.LocationPicker

object Injection {
    fun provideRepository(context: Context) : Repository{
        val database = FoodDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.foodDao())
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val locationPicker = LocationPicker(context)
        val appExecutors = AppExecutors()
        return Repository.getInstance(remoteDataSource, localDataSource, firebaseDatabase,locationPicker, appExecutors)
    }
}