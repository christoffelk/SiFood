package com.capstone.sifood.ui.foodDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.sifood.MainActivity
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.FoodLocation
import com.capstone.sifood.databinding.ActivityFoodDetailBinding
import com.capstone.sifood.other.Constant.LATITUDE
import com.capstone.sifood.other.Constant.LONGITUDE
import com.capstone.sifood.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityFoodDetailBinding
    private val binding get() = _binding

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val type = intent.getStringExtra(TYPE)


        if(type == "popular")
        {
            val foodDetail = intent.getParcelableExtra<Food>(FOOD)
            binding.description.text = foodDetail?.description.toString()
            binding.foodName.text = foodDetail?.name.toString()
            binding.from.text = foodDetail?.province.toString()
            binding.pictureProfile.loadImage(foodDetail?.imgUrl)
            binding.pictureBackground.loadImage(foodDetail?.imgUrl)
        }
        if(type == "Location")
        {
            val foodLocation = intent.getParcelableExtra<FoodLocation>(FOOD)
            binding.description.text = foodLocation?.description.toString()
            binding.foodName.text = foodLocation?.name.toString()
            binding.from.text = foodLocation?.province.toString()
            binding.pictureProfile.loadImage(foodLocation?.imgUrl)
            binding.pictureBackground.loadImage(foodLocation?.imgUrl)
        }
        binding.btnDetailBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnMaps.setOnClickListener {
            //val gmmIntentUri: Uri = Uri.parse("geo:${LATITUDE},${LONGITUDE}?q=${foodDetail?.name}")
            //val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            //mapIntent.setPackage("com.google.android.apps.maps")
            //startActivity(mapIntent)
        }
        val factory = ViewModelFactory.getInstance(this)
        val detailViewModel = ViewModelProvider(this, factory)[FoodDetailViewModel::class.java]
        /*var checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val check = detailViewModel.check(foodDetail?.id.toString())
            withContext(Dispatchers.Main)
            {
                if (check) {
                    binding.btnDetailFavorite.isChecked = true
                    checked = true
                } else {
                    binding.btnDetailFavorite.isChecked = false
                    checked = false
                }
            }
        }
        binding.btnDetailFavorite.setOnClickListener {
            checked = !checked
            if (checked) {
                foodDetail?.let { it1 ->
                    detailViewModel.insertFirebase(it1)
                }
            } else {
                detailViewModel.delete(foodDetail?.id.toString())
                foodDetail?.let {
                    detailViewModel.deleteFirebase(foodDetail)
                }
            }
            binding.btnDetailFavorite.isChecked = checked
        }*/
    }

    private fun addFavoriteToFirestore(data: Food){
        val firestore = Firebase.firestore
        firestore.collection("users")
            .document(auth.uid.toString())
            .collection("favorites")
            .document(data.id.toString())
            .set(data)
    }

    private fun deletefavoriteFirestore(data: Food){
        val firestore = Firebase.firestore
        firestore.collection("users")
            .document(auth.uid.toString())
            .collection("favorites")
            .document(data.id.toString())
            .delete()
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .centerCrop()
            .into(this)
    }

    companion object {
        const val FOOD = "food"
        const val TYPE = "popular"
    }
}