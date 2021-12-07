package com.capstone.sifood.ui.articledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleDetailViewModel : ViewModel() {
    private var _text = MutableLiveData<String>()
    val text  :LiveData<String> = _text
    fun getUrl(url :String)
    {
        _text.value = url
    }

}