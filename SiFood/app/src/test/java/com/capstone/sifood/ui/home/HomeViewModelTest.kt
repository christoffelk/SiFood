package com.capstone.sifood.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.capstone.sifood.data.Repository
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.other.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest{
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<List<Food>>

    @Before
    fun setUp()
    {
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun getFood()
    {
        val dummyList  = DataDummy.listFood()
        val list = MutableLiveData<List<Food>>()
        list.value = dummyList
        Mockito.`when`(repository.getPopularFood()).thenReturn(list)
        val foodEntity = viewModel.getPopularFood().value
        verify(repository).getPopularFood()
        assertNotNull(foodEntity)
        viewModel.getPopularFood().observeForever(observer)
        verify(observer).onChanged(dummyList)
    }

}