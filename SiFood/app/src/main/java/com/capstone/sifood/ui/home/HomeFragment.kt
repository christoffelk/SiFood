package com.capstone.sifood.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.capstone.sifood.R
import com.capstone.sifood.data.firebase.entities.Image
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.FoodLocation
import com.capstone.sifood.databinding.FragmentHomeBinding
import com.capstone.sifood.ui.allfood.AllFoodActivity
import com.capstone.sifood.viewmodel.ViewModelFactory
import com.capstone.sifood.vo.Status

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var secHomeAdapter: HomeAdapterLocation
    private val binding get() = _binding!!

    private lateinit var carouselAdapter: CarouselAdapter
    private var list = ArrayList<Image>()
    private var dots = ArrayList<TextView>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory.getInstance(requireContext())
        homeViewModel =
            ViewModelProvider(this, factory)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter()
        secHomeAdapter = HomeAdapterLocation()
        binding.loading.visibility = View.VISIBLE
        homeViewModel.getPopularFood().observe(viewLifecycleOwner, {
            if(it != null) {
                when (it.status) {
                    Status.LOADING -> binding.loading.visibility = View.VISIBLE
                    Status.ERROR -> {
                        binding.loading.visibility = View.GONE
                        Toast.makeText(context, "Tidak dapat memuat data", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Status.SUCCESS -> {
                        binding.loading.visibility = View.GONE
                        homeAdapter.addItem(it.data as ArrayList<Food>)
                    }
                }
            }
            with(binding.rvPopuler)
            {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = homeAdapter
                setHasFixedSize(true)
            }
        })
        binding.btn1.setOnClickListener {
            Intent(requireContext(), AllFoodActivity::class.java)
                .putExtra(AllFoodActivity.FILTER, "popular")
                .let {
                    startActivity(it)
                }
        }
        binding.btn2.setOnClickListener {
            Intent(requireContext(), AllFoodActivity::class.java)
                .putExtra(AllFoodActivity.FILTER, "location")
                .let {
                    startActivity(it)
                }
        }
        homeViewModel.listFood.observe(viewLifecycleOwner, {
            if(it != null) {
                when (it.status) {
                    Status.LOADING -> binding.loading.visibility = View.VISIBLE
                    Status.ERROR -> {
                        binding.loading.visibility = View.GONE
                        Toast.makeText(context, "Tidak dapat memuat data", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Status.SUCCESS -> {
                        binding.loading.visibility = View.GONE
                        secHomeAdapter.addItem(it.data as ArrayList<FoodLocation>)
                    }
                }
            }

            with(binding.rvDaerah)
            {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = secHomeAdapter
                setHasFixedSize(true)
            }
        })




        homeViewModel.getImageSlider().observe(viewLifecycleOwner, {
            binding.loading.visibility = View.GONE
            list.addAll(it)
            carouselAdapter = CarouselAdapter(list)
            binding.viewPager.adapter = carouselAdapter

            setIndicator()

            binding.viewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    selectedDots(position)
                    super.onPageSelected(position)
                }
            })
        })
    }

    private fun selectedDots(position: Int) {
        for (i in 0 until list.size) {
            if (i == position) {
                dots[i].setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.teal_700
                    )
                )
            } else {
                dots[i].setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.teal_200
                    )
                )
            }
        }
    }

    private fun setIndicator() {
        for (i in 0 until list.size) {
            dots.add(TextView(context))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                dots[i].text = Html.fromHtml("&#9679")
            }
            dots[i].textSize = 18f
            binding.dotsIndicator.addView(dots[i])
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}