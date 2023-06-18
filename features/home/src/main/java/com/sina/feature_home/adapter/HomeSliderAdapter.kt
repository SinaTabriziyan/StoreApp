package com.sina.feature_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.ui_components.loadGlide
import com.sina.model.ui.products_item.ProductsItem
import com.sina.ui_components.databinding.ItemSliderHomeBinding

class HomeSliderAdapter :
    ListAdapter<ProductsItem.Image, HomeSliderAdapter.ViewHolder>(object : DiffUtil.ItemCallback<ProductsItem.Image?>() {
        override fun areItemsTheSame(oldItem: ProductsItem.Image, newItem: ProductsItem.Image): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductsItem.Image, newItem: ProductsItem.Image): Boolean =
            oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemSliderHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductsItem.Image) {
            binding.imgSliderHome.loadGlide(item.src)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSliderHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}
