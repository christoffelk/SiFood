package com.capstone.sifood.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sifood.data.remote.response.ArticlesItem
import com.capstone.sifood.databinding.FragmentArticleBinding
import com.capstone.sifood.viewmodel.ViewModelFactory

class ArticleFragment : Fragment() {

    private lateinit var articleViewModel: ArticleViewModel
    private var _binding: FragmentArticleBinding? = null
    private lateinit var articleAdapter: ArticleAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory.getInstance(requireContext())
        articleViewModel =
            ViewModelProvider(this, factory)[ArticleViewModel::class.java]

        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleAdapter = ArticleAdapter()
        binding.loading.visibility = View.VISIBLE
        articleViewModel.getArticle().observe(viewLifecycleOwner, {
            articleAdapter.addItem(it as ArrayList<ArticlesItem>)
            binding.loading.visibility = View.GONE
            with(binding.rvArticle)
            {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = articleAdapter
                setHasFixedSize(true)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}