package com.capstone.sifood.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.sifood.data.Repository
import com.capstone.sifood.di.Injection
import com.capstone.sifood.ui.allfood.AllFoodViewModel
import com.capstone.sifood.ui.article.ArticleViewModel
import com.capstone.sifood.ui.favorite.FavoriteViewModel
import com.capstone.sifood.ui.foodDetail.FoodDetailViewModel
import com.capstone.sifood.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val repository: Repository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when
        {
            modelClass.isAssignableFrom(ArticleViewModel::class.java) ->
            {
                return ArticleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->
            {
                return FavoriteViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FoodDetailViewModel::class.java) ->
            {
                return FoodDetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
            {
                return HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AllFoodViewModel::class.java) ->
            {
                return AllFoodViewModel(repository) as T
            }
            else -> {
                throw Throwable("Unknown Viewmodel Class : ${modelClass.name}")
            }
        }
    }
    companion object{
        @Volatile
        private var instance :ViewModelFactory? = null

        fun getInstance(context: Context) : ViewModelFactory =
            instance ?: synchronized(this)
            {
                instance?: ViewModelFactory(
                    Injection.provideRepository(context)
                )
            }
    }
}