package com.sina.feature_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.feature_home.databinding.ItemProductsMainBinding
import com.sina.model.ui.products_item.ProductsItem

class MainHomeAdapter(private val onClick: (ProductsItem) -> Unit, private val onReachedEndOfList: (Boolean) -> Unit) :
    ListAdapter<HomeViewModel.MainProducts, MainHomeAdapter.ViewHolder>(object : DiffUtil.ItemCallback<HomeViewModel.MainProducts>() {
        override fun areItemsTheSame(oldItem: HomeViewModel.MainProducts, newItem: HomeViewModel.MainProducts): Boolean = oldItem.title == newItem.title
        override fun areContentsTheSame(oldItem: HomeViewModel.MainProducts, newItem: HomeViewModel.MainProducts): Boolean = oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemProductsMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding) {
                tvProductTitle.text = currentList[adapterPosition].title
                val homeAdapter = HomeAdapter(onClick, onReachedEndOfList)
                rvProductItems.adapter = homeAdapter
                homeAdapter.submitList(currentList[adapterPosition].products)
                // TODO:
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemProductsMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
}