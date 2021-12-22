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
import com.capstone.sifood.data.local.entities.FoodFavorite
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
    private lateinit var detailViewModel: FoodDetailViewModel
    private lateinit var auth : FirebaseAuth
    val foodDetail: FoodFavorite? = null

    var checked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(this, factory)[FoodDetailViewModel::class.java]

        auth = FirebaseAuth.getInstance()
        val type = intent.getStringExtra(TYPE)


        if(type == "popular")
        {
            val data = intent.getParcelableExtra<Food>(FOOD) as Food

            detailViewModel.setDataPopular(data)

            detailViewModel.foodPopular.observe(this,{
                val data = FoodFavorite(
                    id = it.id,
                    name = it.name,
                    province = it.province,
                    provinceEng = it.provinceEng,
                    imageLicence = it.imageLicence,
                    contentLicence = it.contentLicence,
                    imgUrl = it.imgUrl,
                    description = it.description,
                    popular = it.popular
                )
                setData(data)
                CoroutineScope(Dispatchers.IO).launch {
                    val check = detailViewModel.check(data.id.toString())
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
                        data.let { it1 ->
                            detailViewModel.insert(it1)
                        }
                    } else {
                        data.let { it1 ->
                            detailViewModel.delete(it1)
                        }
                    }
                    binding.btnDetailFavorite.isChecked = checked
                }
            })
        }

        if(type == "Location")
        {
            val data = intent.getParcelableExtra<FoodLocation>(FOOD) as FoodLocation

            detailViewModel.setDataLocation(data)

            detailViewModel.foodLocation.observe(this,{
                val data = FoodFavorite(
                    id = it.id,
                    name = it.name,
                    province = it.province,
                    provinceEng = it.provinceEng,
                    imageLicence = it.imageLicence,
                    contentLicence = it.contentLicence,
                    imgUrl = it.imgUrl,
                    description = it.description,
                    popular = it.popular
                )
                setData(data)
                CoroutineScope(Dispatchers.IO).launch {
                    val check = detailViewModel.check(data.id.toString())
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
                        data.let { it1 ->
                            detailViewModel.insert(it1)
                        }
                    } else {
                        data.let { it1 ->
                            detailViewModel.delete(it1)
                        }
                    }
                    binding.btnDetailFavorite.isChecked = checked
                }
            })
        }
        binding.btnDetailBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setData(foodDetail: FoodFavorite) {
        with(binding){
            description.text = foodDetail.description.toString()
            foodName.text = foodDetail.name.toString()
            from.text = foodDetail.province.toString()
            pictureProfile.loadImage(foodDetail.imgUrl)
            pictureBackground.loadImage(foodDetail.imgUrl)
            maps(foodDetail.name.toString())
        }
    }

    private fun maps(name :String)
    {
        binding.btnMaps.setOnClickListener {
            val gmmIntentUri: Uri = Uri.parse("geo:${LATITUDE},${LONGITUDE}?q=${name}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
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
        const val TYPE = "popular"
    }
}