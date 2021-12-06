package com.capstone.sifood.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sifood.data.firebase.entities.Food
import com.capstone.sifood.databinding.ItemlistBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val listUsers = ArrayList<Food>()
    fun addItem(users: ArrayList<Food>) {
        listUsers.clear()
        listUsers.addAll(users)
    }
    class ViewHolder(private val binding: ItemlistBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(food :Food)
        {
            with(binding)
            {
                Glide.with(itemView.context)
                    .load(food.imgUrl)
                    .into(imageView)
                namaMakanan.text = food.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemlistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = listUsers[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int = listUsers.size
}