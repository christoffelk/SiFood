package com.capstone.sifood.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.databinding.FragmentFavoriteBinding
import com.capstone.sifood.viewmodel.ViewModelFactory

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var favoriteAdapter: FavoriteAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = FavoriteAdapter()
        favoriteViewModel.getFavorite().observe(viewLifecycleOwner, {

            if(it.isNotEmpty()){
                favoriteAdapter.addItem(it as ArrayList<Food>)
                with(binding.rvFavorite)
                {
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    setHasFixedSize(true)
                    adapter = favoriteAdapter
                }
            } else {
                binding.emtyImage.visibility = View.VISIBLE
                binding.emptyInformation.visibility = View.VISIBLE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}