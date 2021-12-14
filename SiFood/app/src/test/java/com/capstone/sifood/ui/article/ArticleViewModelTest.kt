package com.capstone.sifood.ui.article

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.capstone.sifood.data.Repository
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.firebase.entities.Resource
import com.capstone.sifood.other.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArticleViewModelTest{
    private lateinit var viewModel: ArticleViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Resource<List<Article>>>

    @Before
    fun setUp()
    {
        viewModel = ArticleViewModel(repository)
    }
    @Test
    fun getArticle()
    {
        val dummyList  = Resource.success(DataDummy.listArticle())
        val list = MutableLiveData<Resource<List<Article>>>()
        list.value = dummyList
        `when`(repository.getArticle()).thenReturn(list)
        val articleEntity = viewModel.getArticle().value?.data
        verify(repository).getArticle()
        assertNotNull(articleEntity)
        viewModel.getArticle().observeForever(observer)
        verify(observer).onChanged(dummyList)
    }
}