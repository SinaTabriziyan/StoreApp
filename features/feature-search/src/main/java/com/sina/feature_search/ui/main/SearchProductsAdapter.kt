package com.sina.feature_search.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.common.utils.loadGlide
import com.sina.feature_search.databinding.ItemSearchedProductsBinding
import com.sina.model.ui.products_item.ProductsItem

class SearchProductsAdapter (private val onClick: (Int) -> Unit) :
    ListAdapter<ProductsItem, SearchProductsAdapter.ViewHolder>(object : DiffUtil.ItemCallback<ProductsItem>() {
        override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean = oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemSearchedProductsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                getItem(adapterPosition).id?.let { id -> onClick(id) }
            }
        }

        fun bind(productsItem: ProductsItem) {
            with(binding) {
                tvProductName.text = productsItem.name
                tvProductPrice.text = productsItem.price
                imgProduct.loadGlide(productsItem.images?.get(0)?.src)
                // TODO: create list of images for item of products
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSearchedProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}