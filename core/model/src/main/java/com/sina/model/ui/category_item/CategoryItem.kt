package com.sina.model.ui.category_item

import com.sina.model.network.category_dto.Image

data class CategoryItem(
    val description: String?,
    val id: Int?,
    val image: Image?,
    val name: String?,
)