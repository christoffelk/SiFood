package com.capstone.sifood.ui.articledetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.capstone.sifood.R

class ArticleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        val webView =findViewById<WebView>(R.id.detail_article)
        val link = intent.getStringExtra(URL) as String
        webView.loadUrl(link)
    }
    companion object{
        const val URL ="url"
    }
}