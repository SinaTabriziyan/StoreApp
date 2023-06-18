package com.sina.feature_search.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.feature_search.ui.adapters.model.SearchFilterItem
import com.sina.feature_search.ui.adapters.model.SearchOrderItem
import com.sina.ui_components.databinding.ItemSearchOrderBinding

class SearchOrderByAdapter(private val onClick: (id: SearchOrderItem) -> Unit) :
    ListAdapter<SearchOrderItem, SearchOrderByAdapter.ViewHolder>(object : DiffUtil.ItemCallback<SearchOrderItem?>() {
        override fun areItemsTheSame(oldItem: SearchOrderItem, newItem: SearchOrderItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SearchOrderItem, newItem: SearchOrderItem): Boolean = oldItem == newItem

    }) {
    inner class ViewHolder(private val binding: ItemSearchOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onClick(getItem(adapterPosition)) }
        }

        fun bind(item: SearchOrderItem) {
            with(binding) {
                tvSearchOrder.text = item.orderTitle
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSearchOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}