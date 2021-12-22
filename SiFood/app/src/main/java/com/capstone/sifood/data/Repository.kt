package com.capstone.sifood.data

import androidx.lifecycle.LiveData
import com.capstone.sifood.data.firebase.database.FirebaseDatabase
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.firebase.entities.Image
import com.capstone.sifood.data.firebase.entities.Resource
import com.capstone.sifood.data.local.LocalDataSource
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.Food2
import com.capstone.sifood.data.remote.NetworkBoundResource
import com.capstone.sifood.data.remote.RemoteDataSource
import com.capstone.sifood.data.remote.response.ApiResponse
import com.capstone.sifood.data.remote.response.ArticlesItem
import com.capstone.sifood.other.AppExecutors
import com.capstone.sifood.other.LocationPicker

class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val firebaseDatabase: FirebaseDatabase,
    private val locationPicker: LocationPicker,
    private val appExecutors: AppExecutors
): AllDataSource{
    override fun getPopularFood(): LiveData<Resource<List<Food>>> {
        return object : NetworkBoundResource<List<Food>,List<Food>>(appExecutors)
        {
            override fun loadFromDB(): LiveData<List<Food>> =
                localDataSource.getAllFood()


            override fun shouldFetch(data: List<Food>?): Boolean =
                data.isNullOrEmpty()


            override fun createCall(): LiveData<ApiResponse<List<Food>>> =
                firebaseDatabase.getPopularFood()


            override fun saveCallResult(data: List<Food>) {
                localDataSource.insertFood(data)
            }

        }.asLiveData()
    }

    override fun getFoodByLocation(location: String): LiveData<Resource<List<Food2>>> {
        return object : NetworkBoundResource<List<Food2>,List<Food2>>(appExecutors)
        {
            override fun loadFromDB(): LiveData<List<Food2>> =
                localDataSource.getLocation(location)

            override fun shouldFetch(data: List<Food2>?): Boolean =
                data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<List<Food2>>> =
                firebaseDatabase.getFoodByLocation(location)

            override fun saveCallResult(data: List<Food2>) {
                localDataSource.insertFoodLocation(data)
            }

        }.asLiveData()
    }

    override fun getFavoriteFood(): LiveData<List<Food>> {
        return localDataSource.getAllFood()
    }

    override fun getImageSlider(): LiveData<List<Image>> {
        return firebaseDatabase.getImageSlider()
    }

    override fun deleteFavoriteFood(id: String) {
        return appExecutors.diskIO().execute {
            localDataSource.deleteFood(id)
        }
    }

    override fun checkFood(id: String) : Boolean {
        return localDataSource.checkFood(id)
    }


    override fun getArticle(): LiveData<Resource<List<Article>>> {
        return object :NetworkBoundResource<List<Article>,List<ArticlesItem>>(appExecutors)
        {
            override fun loadFromDB(): LiveData<List<Article>> =
                localDataSource.getArticle()


            override fun shouldFetch(data: List<Article>?): Boolean =
                data == null ||data.isEmpty()


            override fun createCall(): LiveData<ApiResponse<List<ArticlesItem>>> =
                remoteDataSource.getNews()


            override fun saveCallResult(data: List<ArticlesItem>) {
                val articleList = ArrayList<Article>()
                for (response in data) {
                    val course = Article(title = response.title, year = response.publishedAt, picture = response.urlToImage, url = response.url)
                    articleList.add(course)
                }

                localDataSource.insertArticle(articleList)
            }

        }.asLiveData()
    }

    override fun getLastLocation(): LiveData<List<Double>> {
        val location = locationPicker.getLastLocation()
        return location
    }

    override fun getLocationName(lat: Double, long: Double): LiveData<String> {
        return locationPicker.getLocationName(lat, long)
    }

    override fun insertFavoriteFirebase(data: Food) {
        firebaseDatabase.insertFavoriteFirebase(data)
    }

    override fun deleteFavoriteFirebase(data: Food) {
        firebaseDatabase.deleteFavoriteFirebase(data)
    }

    override fun getFavoriteFromFirebase(uid: String): LiveData<List<Food>> {
        return firebaseDatabase.getFavoriteFirebase(uid)
    }


    companion object{
        @Volatile
        private var instance : Repository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            firebaseDatabase: FirebaseDatabase,
            locationPicker: LocationPicker,
            appExecutors: AppExecutors
        ) : Repository =
            instance?: synchronized(this)
            {
                instance?:Repository(
                    remoteDataSource,
                    localDataSource,
                    firebaseDatabase,
                    locationPicker,
                    appExecutors
                ).apply { instance= this }
            }
    }
}