package com.capstone.sifood.data.firebase.database

import com.capstone.sifood.data.firebase.entities.Food
import com.capstone.sifood.other.Constant.FOOD_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore


class FirebaseDatabase {

    private val firestore =  FirebaseFirestore.getInstance()

    private val foodCollection = firestore.collection(FOOD_COLLECTION)

//    fun getAllFood(): List<Food>{
//        return try {
//            foodCollection.get().await()
//        }
//    }
}