package com.example.favdish.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.favdish.databinding.ItemCustomListBinding
import com.example.favdish.view.activities.AddUpdateDishActivity
import com.example.favdish.view.fragments.AllDishesFragment


class CustomListItemAdapter(
    private val activity: Activity,
    private val fragment: Fragment?,
    private val itemList:List<String>,
    private val selection: String
    ) : RecyclerView.Adapter<CustomListItemAdapter.ViewHolder>(){

    class ViewHolder( itemView: ItemCustomListBinding) : RecyclerView.ViewHolder(itemView.root) {
        val tvText = itemView.tvText
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomListItemAdapter.ViewHolder {
        val binding: ItemCustomListBinding = ItemCustomListBinding.inflate(LayoutInflater.from(activity), parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomListItemAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvText.text = item

        holder.tvText.setOnClickListener {
            if (activity is AddUpdateDishActivity){
                activity.selectedListItem(item,selection)
            }
            if (fragment is AllDishesFragment){
                fragment.filterSelection(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}