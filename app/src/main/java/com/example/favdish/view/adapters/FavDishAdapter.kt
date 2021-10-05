package com.example.favdish.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favdish.databinding.ItemDishLayoutBinding
import com.example.favdish.model.entities.FavDish

class FavDishAdapter(private val fragment: Fragment):RecyclerView.Adapter<FavDishAdapter.ViewHolder>() {

    private var dishes : List<FavDish> = listOf()

    class ViewHolder(view : ItemDishLayoutBinding):RecyclerView.ViewHolder(view.root){
        val ivDishImage = view.ivDishImage
        val ivDIshTitle = view.tvDishTitle
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavDishAdapter.ViewHolder {
        val binding = ItemDishLayoutBinding.inflate(LayoutInflater.from(fragment.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavDishAdapter.ViewHolder, position: Int) {
        val dish = dishes[position]
        Glide.with(fragment)
            .load(dish.image)
            .into(holder.ivDishImage)

        holder.ivDIshTitle.text = dish.title
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    fun dishesList(list:List<FavDish>){
        dishes = list
        notifyDataSetChanged()
    }
}