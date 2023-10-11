package com.example.challenge4fn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge4fn.databinding.MenuItemBinding
import com.example.challenge4fn.items.MenuItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val onItemClick: (MenuItem) -> Unit // Menambahkan parameter onClick
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: MenuItem) {
            // Isi komponen-komponen tampilan dengan data dari objek MenuItem
            binding.imgFood.setImageResource(menuItem.imageRes)
            binding.txtFoodName.text = menuItem.name
            binding.txtFoodPrice.text = "Rp. ${menuItem.price}"

            // Menambahkan onClickListener untuk item
            itemView.setOnClickListener {
                onItemClick(menuItem)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }
}
