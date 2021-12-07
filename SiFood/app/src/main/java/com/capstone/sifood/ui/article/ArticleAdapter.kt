package com.capstone.sifood.ui.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sifood.data.remote.response.ArticlesItem
import com.capstone.sifood.databinding.ArticleListBinding
import com.capstone.sifood.ui.articledetail.ArticleDetailActivity

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    private val listUsers = ArrayList<ArticlesItem>()
    fun addItem(users: ArrayList<ArticlesItem>) {
        listUsers.clear()
        listUsers.addAll(users)
    }

    class ViewHolder(private val binding: ArticleListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem) {
            with(binding)
            {
                tvJudul.text = article.title
                tvTahun.text = article.publishedAt
                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .into(imageView2)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ArticleDetailActivity::class.java)
                intent.putExtra(ArticleDetailActivity.URL, article.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ArticleListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listUsers[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listUsers.size
}