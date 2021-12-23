package com.capstone.sifood.ui.allfood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.sifood.data.Repository
import com.capstone.sifood.data.local.entities.Food
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.launch

class AllFoodViewModel(private val repository: Repository) : ViewModel() {
    private var _data = MutableLiveData<List<Food>>()
    val data: LiveData<List<Food>> = _data


    private lateinit var firestore: FirebaseFirestore

    init {
        viewModelScope.launch {
            firestore = FirebaseFirestore.getInstance()
            firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        }
    }

    fun setData(filter: String, keyword: String = "") {
        when (filter) {
            "search" -> {
                getFoodByKeyword(keyword)
            }
        }
    }

    fun getPopularFood() = repository.getPopularFood()
    fun getFoodByLocation(location: String) = repository.getFoodByLocation(location)

    private fun getFoodByKeyword(keyword: String) {
        val foodCollection = firestore.collection("makanan")
        foodCollection.whereEqualTo("province", keyword)
            .get()
            .addOnSuccessListener { foods ->
                val resultFood = foods.toObjects(Food::class.java)
                val result = ArrayList<Food>()
                resultFood.forEach {
                    result.add(
                        Food(
                            id = it.id,
                            imgUrl = it.imgUrl,
                            name = it.name,
                            province = it.province,
                            description = it.description,
                            popular = it.popular
                        )
                    )
                }
                _data.postValue(result)
            }
    }

}