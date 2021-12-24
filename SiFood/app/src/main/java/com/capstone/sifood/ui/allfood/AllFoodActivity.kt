package com.capstone.sifood.ui.allfood

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.sifood.R
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.FoodLocation
import com.capstone.sifood.databinding.ActivityAllFoodBinding
import com.capstone.sifood.other.Constant.LOCATION_NAME
import com.capstone.sifood.viewmodel.ViewModelFactory
import com.capstone.sifood.vo.Status
import java.util.*
import kotlin.collections.ArrayList


class AllFoodActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityAllFoodBinding
    private lateinit var allFoodAdapter: AllFoodAdapter
    private lateinit var viewModel: AllFoodViewModel
    private lateinit var allFoodAdapterLocation: AllFoodAdapterLocation

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_food)

        binding = ActivityAllFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allFoodAdapter = AllFoodAdapter()
        allFoodAdapterLocation = AllFoodAdapterLocation()
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this,factory)[AllFoodViewModel::class.java]

        val filter = intent.getStringExtra(FILTER).toString()

        when (filter) {
            "popular" -> {
                binding.textResult.text = getString(R.string.GoInter)
                viewModel.getPopularFood().observe(this,{
                    if(it != null) {
                        when (it.status) {
                            Status.LOADING -> {}
                            Status.ERROR -> {
                                Toast.makeText(this, "Tidak dapat memuat data ", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            Status.SUCCESS -> {
                                allFoodAdapter.addItem(it.data as ArrayList<Food>)
                            }
                        }
                    }

                    with(binding.rvAllFood) {
                        layoutManager = GridLayoutManager(this@AllFoodActivity, 2)
                        setHasFixedSize(false)
                        adapter = allFoodAdapter
                    }
                })
            }
            "location" -> {
                binding.textResult.text = getString(R.string.Daerah) + LOCATION_NAME
                viewModel.getFoodByLocation(LOCATION_NAME).observe(this,{
                    if(it != null) {
                        when (it.status) {
                            Status.LOADING -> {}
                            Status.ERROR -> {
                                Toast.makeText(this, "Tidak dapat memuat data", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            Status.SUCCESS -> {
                                allFoodAdapterLocation.addItem(it.data as ArrayList<FoodLocation>)
                            }
                        }
                    }
                    with(binding.rvAllFood) {
                        layoutManager = GridLayoutManager(this@AllFoodActivity, 2)
                        setHasFixedSize(false)
                        adapter = allFoodAdapterLocation
                    }
                })
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

    @SuppressLint("SetTextI18n")
    override fun onQueryTextSubmit(key: String?): Boolean {
        if (key != null) {
            capitalizeString(key)?.let { viewModel.setData("search", it) }
            viewModel.data.observe(this, {
                val data = it as ArrayList<Food>
                if (data.isNotEmpty()) {
                    allFoodAdapter.addItem(data)
                    binding.textResult.text = "Makanan dengan keyword  \'$key\'"
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
    fun capitalizeString(string: String?): String? {
        val chars = string?.lowercase(Locale.getDefault())?.toCharArray()
        var found = false
        if (chars != null) {
            for (i in chars.indices) {
                if (!found && Character.isLetter(chars[i])) {
                    chars[i] = Character.toUpperCase(chars[i])
                    found = true
                } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                    found = false
                }
            }
        }
        return chars?.let { String(it) }
    }
}