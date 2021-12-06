package com.capstone.sifood.data.remote.api

import com.capstone.sifood.data.remote.response.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("everything")
    fun getNews(
        @Query("q") q: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Call<ApiResponse>
}