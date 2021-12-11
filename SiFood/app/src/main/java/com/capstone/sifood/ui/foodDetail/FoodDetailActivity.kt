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
import com.capstone.sifood.databinding.ActivityFoodDetailBinding
import com.capstone.sifood.other.Constant.LATITUDE
import com.capstone.sifood.other.Constant.LONGITUDE
import com.capstone.sifood.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityFoodDetailBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val foodDetail = intent.getParcelableExtra<Food>(FOOD)
        binding.description.text = foodDetail?.description.toString()
        binding.foodName.text = foodDetail?.name.toString()
        binding.from.text = foodDetail?.province.toString()
        binding.pictureProfile.loadImage(foodDetail?.imgUrl)
        binding.pictureBackground.loadImage(foodDetail?.imgUrl)
        binding.btnDetailBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnMaps.setOnClickListener {
            val gmmIntentUri: Uri = Uri.parse("geo:${LATITUDE},${LONGITUDE}?q=${foodDetail?.name}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        val factory = ViewModelFactory.getInstance(this)
        val detailViewModel = ViewModelProvider(this, factory)[FoodDetailViewModel::class.java]
        var checked = false
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
                foodDetail?.let { it1 -> detailViewModel.insert(it1) }
            } else {
                detailViewModel.delete(foodDetail?.id.toString())
            }
            binding.btnDetailFavorite.isChecked = checked
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .centerCrop()
            .into(this)
    }

    companion object {
        const val FOOD = "food"
    }

}