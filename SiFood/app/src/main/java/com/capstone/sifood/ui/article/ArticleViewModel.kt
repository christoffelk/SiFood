package com.capstone.sifood.ui.article

import androidx.lifecycle.ViewModel
import com.capstone.sifood.data.Repository

class ArticleViewModel(private val repository: Repository) : ViewModel() {
    fun getArticle()  = repository.getArticle()
}