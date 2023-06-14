package com.sina.feature_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.feature_home.HomeViewModel
import com.sina.model.ui.products_item.ProductsItem
import com.sina.ui_components.databinding.ItemProductsBinding
import com.sina.ui_components.databinding.ItemProductsMainBinding
import com.sina.ui_components.loadGlide

class MainHomeAdapter(private val onClick: (Int) -> Unit, private val onReachedEndOfList: (Boolean) -> Unit) :
    ListAdapter<HomeViewModel.MainProducts, MainHomeAdapter.ViewHolder>(object : DiffUtil.ItemCallback<HomeViewModel.MainProducts>() {
        override fun areItemsTheSame(oldItem: HomeViewModel.MainProducts, newItem: HomeViewModel.MainProducts): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: HomeViewModel.MainProducts, newItem: HomeViewModel.MainProducts): Boolean =
            oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemProductsMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding) {
                tvProductTitle.text = currentList[adapterPosition].title
                val homeAdapter = HomeAdapter(onClick, onReachedEndOfList)
                rvProductItems.adapter = homeAdapter
                homeAdapter.submitList(currentList[adapterPosition].products)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemProductsMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
    class HomeAdapter(private val onClick: (Int) -> Unit, private val onReachedEndOfList: (Boolean) -> Unit) :
        ListAdapter<ProductsItem, HomeAdapter.ViewHolder>(object : DiffUtil.ItemCallback<ProductsItem>() {
            override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean = oldItem == newItem
        }) {
        inner class ViewHolder(private val binding: ItemProductsBinding) : RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    getItem(adapterPosition).id?.let { it1 -> onClick.invoke(it1) }
                }
            }
            fun bind(productsItem: ProductsItem) {
                with(binding) {
                    tvProductName.text = productsItem.name
                    tvProductPrice.text = productsItem.price
                    imgProduct.loadGlide(productsItem.images?.get(0)?.src)
                    if (adapterPosition == itemCount - 1) onReachedEndOfList.invoke(true) else onReachedEndOfList.invoke(false)
                }
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
    }
}