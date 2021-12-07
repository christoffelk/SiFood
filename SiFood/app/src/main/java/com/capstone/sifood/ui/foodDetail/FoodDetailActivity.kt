package com.capstone.sifood.ui.foodDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.capstone.sifood.R
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.databinding.ActivityFoodDetailBinding

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityFoodDetailBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val foodDetail = intent.getParcelableExtra<Food>(FOOD)
        binding.description.text = foodDetail?.description.toString()
        binding.foodName.text = foodDetail?.name.toString()
        binding.from.text = foodDetail?.province.toString()
        binding.pictureProfile.loadImage(foodDetail?.imgUrl)
        binding.pictureBackground.loadImage(foodDetail?.imgUrl)
    }
    private fun ImageView.loadImage(url : String?)
    {
        Glide.with(this.context)
            .load(url)
            .centerCrop()
            .into(this)
    }
    companion object{
        const val FOOD ="food"
    }

}