package com.sina.feature_item.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.model.data.product_dto.Image
import com.sina.ui_components.databinding.ItemImageProductBinding
import com.sina.ui_components.loadGlide

class ImageItemAdapter : ListAdapter<Image, ImageItemAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Image?>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean = oldItem == newItem
}) {
    inner class ViewHolder(private val binding: ItemImageProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image) {
            with(binding) {
                imgItemProduct.loadGlide(item.src)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemImageProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

}
