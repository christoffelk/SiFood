package com.capstone.sifood.data.firebase.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.sifood.data.firebase.entities.Image
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.FoodFavorite
import com.capstone.sifood.data.local.entities.FoodLocation
import com.capstone.sifood.data.remote.response.ApiResponse
import com.capstone.sifood.other.Constant.FOOD_COLLECTION
import com.capstone.sifood.other.Constant.IMAGE_COLLECTION
import com.capstone.sifood.other.EspressoIdlingResource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class FirebaseDatabase {

    private val firestore =  FirebaseFirestore.getInstance()

    private val foodCollection = firestore.collection(FOOD_COLLECTION)

    private val imgCollection = firestore.collection(IMAGE_COLLECTION)

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()


    fun getPopularFood(): LiveData<ApiResponse<List<Food>>>{
        val food = MutableLiveData<ApiResponse<List<Food>>>()
        EspressoIdlingResource.increment()
        return try {
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
                                popular = it.popular,
                                provinceEng = it.provinceEng,
                                contentLicence = it.contentLicence,
                                imageLicence = it.imageLicence
                            )
                        )
                    }
                    food.value = ApiResponse.success(result)
                    EspressoIdlingResource.decrement()
                }
            food
        } catch (e: Exception) {
            food.value = ApiResponse.success(emptyList())
            food
        }
    }

    fun getFoodByLocation(location: String): LiveData<ApiResponse<List<FoodLocation>>>{
        val food = MutableLiveData<ApiResponse<List<FoodLocation>>>()
        EspressoIdlingResource.increment()
        return try {
            /*food.value = foodCollection
                .whereEqualTo("province", location)
                .get()
                .result.toObjects(Food::class.java)*/
            foodCollection
                .whereEqualTo("provinceEng", location)
                .get()
                .addOnSuccessListener { foods ->
                    val resultFood = foods.toObjects(FoodLocation::class.java)
                    val result = ArrayList<FoodLocation>()
                    resultFood.forEach {
                        result.add(
                            FoodLocation(
                                id = it.id,
                                imgUrl = it.imgUrl,
                                name = it.name,
                                province = it.province,
                                description = it.description,
                                popular = it.popular,
                                provinceEng = it.provinceEng,
                                contentLicence = it.contentLicence,
                                imageLicence = it.imageLicence
                            )
                        )
                    }
                    food.value = ApiResponse.success(result)
                    EspressoIdlingResource.decrement()
                }
            food
        } catch (e: Exception) {
            food
        }
    }
    fun getImageSlider() : LiveData<List<Image>>{
        val food = MutableLiveData<List<Image>>()
        EspressoIdlingResource.increment()
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
                EspressoIdlingResource.decrement()
            }
        return food
    }

    fun insertFavoriteFirebase(data: Food){
        val firestore = Firebase.firestore
        firestore.collection("users")
            .document(auth.uid.toString())
            .collection("favorites")
            .document(data.id.toString())
            .set(data)
    }

    fun deleteFavoriteFirebase(data: Food){
        val firestore = Firebase.firestore
        firestore.collection("users")
            .document(auth.uid.toString())
            .collection("favorites")
            .document(data.id.toString())
            .delete()
    }

    fun getFavoriteFirebase(uid: String): LiveData<ApiResponse<List<FoodFavorite>>>{
        val firestore = Firebase.firestore
        val food = MutableLiveData<ApiResponse<List<FoodFavorite>>>()
        val favoriteData = firestore.collection("users")
            .document(uid)
            .collection("favorites")
        favoriteData.get().addOnSuccessListener { favorites ->
            val favRes = favorites.toObjects(FoodFavorite::class.java)
            val result = ArrayList<FoodFavorite>()
            favRes.forEach {
                result.add(
                    FoodFavorite(
                        id = it.id,
                        name = it.name,
                        province = it.province,
                        provinceEng = it.provinceEng,
                        popular = it.popular,
                        imgUrl = it.imgUrl,
                        imageLicence = it.imageLicence,
                        contentLicence = it.description,
                        description = it.description
                    )
                )
            }
            food.value = ApiResponse.success(result)
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