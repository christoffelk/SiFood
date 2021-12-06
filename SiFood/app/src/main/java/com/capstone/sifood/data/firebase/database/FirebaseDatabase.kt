package com.capstone.sifood.data.firebase.database

import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.other.Constant.FOOD_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception


class FirebaseDatabase {

    private val firestore =  FirebaseFirestore.getInstance()

    private val foodCollection = firestore.collection(FOOD_COLLECTION)


    fun getPopularFood(): List<Food>{
        return try {
            foodCollection
                .whereEqualTo("popular", true)
                .get()
                .result.toObjects(Food::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getFoodByLocation(location: String): List<Food>{
        return try {
            foodCollection
                .whereEqualTo("province", location)
                .get()
                .result.toObjects(Food::class.java)
        } catch (e: Exception) {
            emptyList()
        }
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