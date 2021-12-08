package com.capstone.sifood.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.sifood.data.Repository
import com.capstone.sifood.di.Injection
import com.capstone.sifood.ui.article.ArticleViewModel
import com.capstone.sifood.ui.favorite.FavoriteViewModel

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