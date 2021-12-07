package com.capstone.sifood.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.databinding.FragmentHomeBinding
import com.capstone.sifood.other.Constant.LOCATION_NAME
import com.google.firebase.FirebaseApp
import java.lang.StringBuilder

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeAdapter: HomeAdapter

    private lateinit var secHomeAdapter: HomeAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter()
        secHomeAdapter = HomeAdapter()

        homeViewModel.home.observe(viewLifecycleOwner,{
            homeAdapter.addItem(it as ArrayList<Food>)
            with(binding.rvPopuler)
            {
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                adapter = homeAdapter
                setHasFixedSize(true)
            }
        })

        homeViewModel.foodByLocation.observe(viewLifecycleOwner, {
            secHomeAdapter.addItem(it as ArrayList<Food>)
            with(binding.rvDaerah)
            {
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                adapter = secHomeAdapter
                setHasFixedSize(true)
            }
        })

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}