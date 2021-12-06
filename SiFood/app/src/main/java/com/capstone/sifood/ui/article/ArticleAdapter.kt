package com.capstone.sifood.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.databinding.ArticleListBinding

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    private val listUsers = ArrayList<Article>()
    fun addItem(users: ArrayList<Article>) {
        listUsers.clear()
        listUsers.addAll(users)
    }
    class ViewHolder(private val binding : ArticleListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article)
        {
            with(binding)
            {
                tvJudul.text = article.title
                tvTahun.text = article.year.toString()
                Glide.with(itemView.context)
                    .load(article.picture)
                    .into(imageView2)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder {
        return ViewHolder(ArticleListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        val item = listUsers[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listUsers.size
}