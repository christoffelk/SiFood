package com.capstone.sifood.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.sifood.R
import com.capstone.sifood.data.local.entities.FoodLocation
import com.capstone.sifood.databinding.ItemlistBinding
import com.capstone.sifood.ui.foodDetail.FoodDetailActivity

class HomeAdapterLocation : RecyclerView.Adapter<HomeAdapterLocation.ViewHolder>() {
    private val listUsers = ArrayList<FoodLocation>()
    fun addItem(users: ArrayList<FoodLocation>) {
        listUsers.clear()
        listUsers.addAll(users)
    }

    class ViewHolder(private val binding: ItemlistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FoodLocation) {
            with(binding)
            {
                Glide.with(itemView.context)
                    .load(food.imgUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imageView)
                namaMakanan.text = food.name
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, FoodDetailActivity::class.java)
                intent.putExtra(FoodDetailActivity.FOOD, food)
                intent.putExtra(FoodDetailActivity.TYPE,"Location")
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

    override fun getItemCount(): Int {
        return if (listUsers.size < 10) {
            listUsers.size
        } else {
            10
        }
    }
}