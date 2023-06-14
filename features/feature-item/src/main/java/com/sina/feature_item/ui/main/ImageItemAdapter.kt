package com.sina.feature_item.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.model.data.product_dto.Image
import com.sina.model.ui.product_details_item.ProductDetails
import com.sina.model.ui.products_item.ProductsItem
import com.sina.ui_components.databinding.ItemImageProductBinding
import com.sina.ui_components.loadGlide

class ImageItemAdapter : ListAdapter<ProductDetails.Image, ImageItemAdapter.ViewHolder>(object : DiffUtil.ItemCallback<ProductDetails.Image?>() {
    override fun areItemsTheSame(oldItem: ProductDetails.Image, newItem: ProductDetails.Image): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ProductDetails.Image, newItem: ProductDetails.Image): Boolean = oldItem == newItem
}) {
    inner class ViewHolder(private val binding: ItemImageProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetails.Image) {
            with(binding) {
                imgItemProduct.loadGlide(item.src)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemImageProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

}
