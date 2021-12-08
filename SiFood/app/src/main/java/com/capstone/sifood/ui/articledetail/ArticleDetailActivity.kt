package com.capstone.sifood.ui.articledetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.lifecycle.ViewModelProvider
import com.capstone.sifood.R
import com.capstone.sifood.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityArticleDetailBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val articleDetailViewModel = ViewModelProvider(this)[ArticleDetailViewModel::class.java]
        val webView =findViewById<WebView>(R.id.detail_article)
        val link = intent.getStringExtra(URL) as String
        binding.loading.visibility = View.VISIBLE
        articleDetailViewModel.getUrl(link)
        articleDetailViewModel.text.observe(this,{
            webView.loadUrl(it)
            binding.loading.visibility = View.GONE
        })


    }
    companion object{
        const val URL ="url"
    }
}