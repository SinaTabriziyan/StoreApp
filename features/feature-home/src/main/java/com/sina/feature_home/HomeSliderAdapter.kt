package com.sina.feature_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.common.utils.loadGlide
import com.sina.feature_home.databinding.ItemSliderHomeBinding
import com.sina.model.data.products_dto.Image

class HomeSliderAdapter : ListAdapter<Image, HomeSliderAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean = oldItem == newItem
    }

    inner class ViewHolder(private val binding: ItemSliderHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image) {
            with(binding) {
                imgSliderHome.loadGlide(item.src)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSliderHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

}
