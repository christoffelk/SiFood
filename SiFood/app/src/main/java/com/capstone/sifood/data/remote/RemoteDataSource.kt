package com.capstone.sifood.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.sifood.data.remote.api.ApiConfig
import com.capstone.sifood.data.remote.response.ApiResponse
import com.capstone.sifood.data.remote.response.ArticlesItem
import com.capstone.sifood.data.remote.response.NewsResponse
import com.capstone.sifood.other.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {
        const val API_KEY = "295e45c5e1dc4e99b25ca739e5cbc957"
        const val Q = "makanan-tradisional"
        const val SHORTBY = "publishedAt"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getNews(): LiveData<ApiResponse<List<ArticlesItem>>> {
        EspressoIdlingResource.increment()
        val resultNews = MutableLiveData<ApiResponse<List<ArticlesItem>>>()

        val client = ApiConfig.getApiSevice().getNews(Q, SHORTBY, API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        resultNews.value = ApiResponse.success(responseBody.articles)
                        EspressoIdlingResource.decrement()
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getDetailTvShow onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }

        })
        return resultNews
    }
}