package com.capstone.sifood.ui.allfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.sifood.R
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.databinding.ActivityAllFoodBinding
import com.capstone.sifood.other.Constant.LOCATION_NAME

class AllFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllFoodBinding
    private lateinit var allFoodAdapter: AllFoodAdapter
    private lateinit var viewModel: AllFoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_food)

        binding = ActivityAllFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allFoodAdapter = AllFoodAdapter()

        viewModel = ViewModelProvider(this).get(AllFoodViewModel::class.java)

        val filter = intent.getStringExtra(FILTER).toString()

        when(filter){
            "popular" ->{
                binding.textResult.text = "Makanan tradisional Indonesia Go Internasional"
            }
            "location" -> {
                binding.textResult.text = "Makanan tradisional Indonesia di daerah "+ LOCATION_NAME
            }
        }
        viewModel.setData(filter)

        viewModel.data.observe(this, { data ->
            allFoodAdapter.addItem(data as ArrayList<Food>)
            with(binding.rvAllFood){
                layoutManager = GridLayoutManager(this@AllFoodActivity, 2)
                setHasFixedSize(true)
                adapter = allFoodAdapter
            }
        })
    }

    companion object {
        const val FILTER = "filter"
    }
}