package com.capstone.sifood.ui.allfood

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

class AllFoodAdapterLocation : RecyclerView.Adapter<AllFoodAdapterLocation.ViewHolder>() {
    private val listFoods = ArrayList<FoodLocation>()
    fun addItem(foods: ArrayList<FoodLocation>) {
        listFoods.clear()
        listFoods.addAll(foods)
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
        val food = listFoods[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int = listFoods.size
}