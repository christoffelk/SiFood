package com.capstone.sifood.ui.home

import androidx.lifecycle.*
import com.capstone.sifood.data.firebase.entities.Image
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.other.Constant.LOCATION_NAME
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _home = MutableLiveData<List<Food>>()
    val home : LiveData<List<Food>> = _home

    private val _foodByLocation = MutableLiveData<List<Food>>()
    val foodByLocation : LiveData<List<Food>> = _foodByLocation

    private val _imgCarousel = MutableLiveData<List<Image>>()
    val imgCarousel: LiveData<List<Image>> = _imgCarousel


    private lateinit var firestore: FirebaseFirestore

    init {
        viewModelScope.launch {
            firestore = FirebaseFirestore.getInstance()
            firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
            getPopularFood()
            getFoodByLocation(LOCATION_NAME)
            getImageSlider()
        }
    }

    private fun getImageSlider(){
        val imgCollection = firestore.collection("banner")
        imgCollection
            .get()
            .addOnSuccessListener { imgs ->
                val imgRes = imgs.toObjects(Image::class.java)
                var result = ArrayList<Image>()

                imgRes.forEach {
                    result.add(
                        Image(
                            id = it.id,
                            imageUrl = it.imageUrl
                        )
                    )
                }

                _imgCarousel.value = result
            }
    }

    private fun getPopularFood(){
        val foodCollection = firestore.collection("makanan")
        foodCollection
            .whereEqualTo("popular", true)
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
                _home.value = result
            }
    }

    private fun getFoodByLocation(location: String){
        val foodCollection = firestore.collection("makanan")
        foodCollection
            .whereEqualTo("province", location)
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
                _foodByLocation.value = result
            }
    }

}