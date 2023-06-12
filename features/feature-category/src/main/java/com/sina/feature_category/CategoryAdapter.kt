package com.sina.feature_category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.common.utils.loadGlide
import com.sina.feature_category.databinding.ItemCategoryBinding
import com.sina.model.ui.category_item.CategoryItem

class CategoryAdapter(
    private val onClick: (Int) -> Unit,
) : ListAdapter<CategoryItem, CategoryAdapter.ViewHolder>(object : DiffUtil.ItemCallback<CategoryItem>() {
    override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean = oldItem.id == newItem.id
}) {
    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.apply {
                setOnClickListener { getItem(adapterPosition).id?.let { id -> onClick(id) } }
            }
        }

        fun bind(categoryDTOItem: CategoryItem) {
            binding.tvCategoryName.text = categoryDTOItem.name
            categoryDTOItem.image?.src?.let { binding.imvCategory.loadGlide(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder =
        ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) = holder.bind(getItem(position))
}