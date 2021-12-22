package com.capstone.sifood.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.data.local.entities.FoodFavorite
import com.capstone.sifood.data.local.entities.FoodLocation
import com.capstone.sifood.databinding.FragmentFavoriteBinding
import com.capstone.sifood.viewmodel.ViewModelFactory
import com.capstone.sifood.vo.Status
import com.google.firebase.auth.FirebaseAuth

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var favoriteAdapter: FavoriteAdapter

    private lateinit var auth: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory.getInstance(requireContext())
        favoriteViewModel =
            ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = FavoriteAdapter()
//        favoriteViewModel.getFavoriteFromFirebase(auth.uid.toString()).observe(viewLifecycleOwner, {
//            if(it != null) {
//                when (it.status) {
//                    Status.LOADING -> {
//                    }
//                    Status.ERROR -> {
//                        Toast.makeText(context, "Tidak dapat memuat data", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                    Status.SUCCESS -> {
//                        favoriteAdapter.addItem(it.data as ArrayList<FoodFavorite>)
//                        with(binding.rvFavorite)
//                        {
//                            layoutManager = GridLayoutManager(requireContext(), 2)
//                            setHasFixedSize(true)
//                            adapter = favoriteAdapter
//                        }
//                    }
//                }
//
//            }
//            if(it.data?.isEmpty() == true)
//            {
//                binding.emtyImage.visibility = View.VISIBLE
//                binding.emptyInformation.visibility = View.VISIBLE
//            }
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}