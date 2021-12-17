package com.capstone.sifood.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.capstone.sifood.data.firebase.database.FirebaseDatabase
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.local.LocalDataSource
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.remote.RemoteDataSource
import com.capstone.sifood.other.AppExecutors
import com.capstone.sifood.other.DataDummy
import com.capstone.sifood.other.LiveDataTestUtil
import com.capstone.sifood.other.LocationPicker
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class RepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val firebaseDatabase = mock(FirebaseDatabase::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val localPicker = mock(LocationPicker::class.java)
    private val repository = FakeRepository(remote, local,firebaseDatabase,localPicker, appExecutors)

    @Test
    fun getPopularFood()
    {
        val dummyFood = MutableLiveData<List<Food>>()
        dummyFood.value = DataDummy.listFood()
        `when`(firebaseDatabase.getPopularFood()).thenReturn(dummyFood)
        val foodEntities = LiveDataTestUtil.getValue(repository.getPopularFood())
        verify(firebaseDatabase).getPopularFood()
        assertNotNull(foodEntities)
    }

    @Test
    fun getArticle()
    {
        val dummyArticle = MutableLiveData<List<Article>>()
        dummyArticle.value = DataDummy.listArticle()
        `when`(local.getArticle()).thenReturn(dummyArticle)
        val articleEntities = LiveDataTestUtil.getValue(repository.getArticle())
        verify(local).getArticle()
        assertNotNull(articleEntities.data)
    }

    @Test
    fun getFavorite()
    {
        val dummyFood = MutableLiveData<List<Food>>()
        dummyFood.value  = DataDummy.listFood()
        `when`(local.getAllFood()).thenReturn(dummyFood)
        val foodEntities = LiveDataTestUtil.getValue(repository.getFavoriteFood())
        verify(local).getAllFood()
        assertNotNull(foodEntities)
    }
}