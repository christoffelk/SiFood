package com.capstone.sifood.data.firebase.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.sifood.data.firebase.entities.Image
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.other.Constant.FOOD_COLLECTION
import com.capstone.sifood.other.Constant.IMAGE_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception


class FirebaseDatabase {

    private val firestore =  FirebaseFirestore.getInstance()

    private val foodCollection = firestore.collection(FOOD_COLLECTION)

    private val imgCollection = firestore.collection(IMAGE_COLLECTION)


    fun getPopularFood(): LiveData<List<Food>>{
        val food = MutableLiveData<List<Food>>()
        return try {
            /*food.value = foodCollection
                .whereEqualTo("popular", true)
                .get()
                .result.toObjects(Food::class.java)*/
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
                    food.value = result
                }
            food
        } catch (e: Exception) {
            food.value = emptyList()
            food
        }
    }

    fun getFoodByLocation(location: String): LiveData<List<Food>>{
        val food = MutableLiveData<List<Food>>()
        return try {
            /*food.value = foodCollection
                .whereEqualTo("province", location)
                .get()
                .result.toObjects(Food::class.java)*/
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
                    food.value = result
                }
            food
        } catch (e: Exception) {
            food
        }
    }
    fun getImageSlider() : LiveData<List<Image>>{
        val food = MutableLiveData<List<Image>>()
        imgCollection
            .get()
            .addOnSuccessListener { imgs ->
                val imgRes = imgs.toObjects(Image::class.java)
                val result = ArrayList<Image>()

                imgRes.forEach {
                    result.add(
                        Image(
                            id = it.id,
                            imageUrl = it.imageUrl
                        )
                    )
                }
                food.value = result
            }
        return food
    }
    companion object{
        @Volatile
        private var instance : FirebaseDatabase? = null

        fun getInstance() : FirebaseDatabase =
            instance ?: synchronized(this)
            {
                instance ?: FirebaseDatabase()
            }
    }
}