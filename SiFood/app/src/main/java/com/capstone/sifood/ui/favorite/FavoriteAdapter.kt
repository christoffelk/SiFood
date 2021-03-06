package com.capstone.sifood.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sifood.data.local.entities.FoodFavorite
import com.capstone.sifood.databinding.ItemlistBinding
import com.capstone.sifood.ui.foodDetail.FoodDetailActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val listUsers = ArrayList<FoodFavorite>()
    fun addItem(users: ArrayList<FoodFavorite>) {
        listUsers.clear()
        listUsers.addAll(users)
    }

    class ViewHolder(private val binding: ItemlistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FoodFavorite) {
            with(binding)
            {
                Glide.with(itemView.context)
                    .load(food.imgUrl)
                    .into(imageView)
                namaMakanan.text = food.name
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, FoodDetailActivity::class.java)
                intent.putExtra(FoodDetailActivity.FOOD, food)
                intent.putExtra(FoodDetailActivity.TYPE, "favorite")
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = listUsers[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int = listUsers.size
}