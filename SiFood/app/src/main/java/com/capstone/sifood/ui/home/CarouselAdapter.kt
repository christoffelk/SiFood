package com.capstone.sifood.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sifood.data.firebase.entities.Image
import com.capstone.sifood.databinding.ItemSlideBinding

class CarouselAdapter (private val listImage: List<Image>): RecyclerView.Adapter<CarouselAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: ItemSlideBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Image){
            with(binding){
                Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .into(ivSlider)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listImage[position])
    }

    override fun getItemCount(): Int = listImage.size
}