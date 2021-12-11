package com.capstone.sifood.ui.allfood

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.sifood.R
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.databinding.ActivityAllFoodBinding
import com.capstone.sifood.other.Constant.LOCATION_NAME


class AllFoodActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityAllFoodBinding
    private lateinit var allFoodAdapter: AllFoodAdapter
    private lateinit var viewModel: AllFoodViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_food)

        binding = ActivityAllFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allFoodAdapter = AllFoodAdapter()

        viewModel = ViewModelProvider(this)[AllFoodViewModel::class.java]

        val filter = intent.getStringExtra(FILTER).toString()

        when (filter) {
            "popular" -> {
                binding.textResult.text = getString(R.string.GoInter)
            }
            "location" -> {
                binding.textResult.text = getString(R.string.Daerah) + LOCATION_NAME
            }
        }
        viewModel.setData(filter)

        viewModel.data.observe(this, { data ->
            allFoodAdapter.addItem(data as ArrayList<Food>)
            with(binding.rvAllFood) {
                layoutManager = GridLayoutManager(this@AllFoodActivity, 2)
                setHasFixedSize(false)
                adapter = allFoodAdapter
            }
        })

        binding.searchView.setOnQueryTextListener(this)

    }

    companion object {
        const val FILTER = "filter"
    }

    override fun onQueryTextSubmit(key: String?): Boolean {
        if (key != null) {
            viewModel.setData("search", key)
            viewModel.data.observe(this, {
                val data = it as ArrayList<Food>
                if (data.isNotEmpty()) {
                    allFoodAdapter.addItem(data)
                    binding.textResult.text = "Makanan dengan keyword \'$key\'"
                } else {
                    binding.textResult.text = "Makanan dengan keyword \'$key\' tidak ditemukan"
                }
            })
            return true
        }
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }
}