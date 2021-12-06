package com.capstone.sifood.data

import androidx.lifecycle.LiveData
import com.capstone.sifood.data.firebase.database.FirebaseDatabase
import com.capstone.sifood.data.local.LocalDataSource
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.remote.RemoteDataSource
import com.capstone.sifood.data.remote.response.ArticlesItem
import com.capstone.sifood.other.AppExecutors

class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val firebaseDatabase: FirebaseDatabase,
    private val appExecutors: AppExecutors
): AllDataSource{
    override fun getPopularFood(): List<Food> {
        return firebaseDatabase.getPopularFood()

    }

    override fun getFoodByLocation(location: String): List<Food> {
        return firebaseDatabase.getFoodByLocation(location)
    }

    override fun getFavoriteFood(): List<Food> {
        return localDataSource.getAllFood()
    }

    override fun insertFavoriteFood(data: Food) {
        return appExecutors.diskIO().execute {
            localDataSource.insertFood(data)
        }
    }

    override fun deleteFavoriteFood(id: String) {
        return appExecutors.diskIO().execute {
            localDataSource.deleteFood(id)
        }
    }

    override fun getArticle(): LiveData<List<ArticlesItem>> {
        return remoteDataSource.getNews()
    }
    companion object{
        @Volatile
        private var instance : Repository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            firebaseDatabase: FirebaseDatabase,
            appExecutors: AppExecutors
        ) : Repository =
            instance?: synchronized(this)
            {
                instance?:Repository(
                    remoteDataSource,
                    localDataSource,
                    firebaseDatabase,
                    appExecutors
                ).apply { instance= this }
            }
    }
}