package com.capstone.sifood.data

import androidx.lifecycle.LiveData
import com.capstone.sifood.data.firebase.database.FirebaseDatabase
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.firebase.entities.Image
import com.capstone.sifood.data.firebase.entities.Resource
import com.capstone.sifood.data.local.LocalDataSource
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.remote.NetworkBoundResource
import com.capstone.sifood.data.remote.RemoteDataSource
import com.capstone.sifood.data.remote.response.ApiResponse
import com.capstone.sifood.data.remote.response.ArticlesItem
import com.capstone.sifood.other.AppExecutors

class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val firebaseDatabase: FirebaseDatabase,
    private val appExecutors: AppExecutors
): AllDataSource{
    override fun getPopularFood(): LiveData<List<Food>> {
        return firebaseDatabase.getPopularFood()

    }

    override fun getFoodByLocation(location: String): LiveData<List<Food>> {
        return firebaseDatabase.getFoodByLocation(location)
    }

    override fun getFavoriteFood(): LiveData<List<Food>> {
        return localDataSource.getAllFood()
    }

    override fun insertFavoriteFood(data: Food) {
        return appExecutors.diskIO().execute {
            localDataSource.insertFood(data)
        }
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