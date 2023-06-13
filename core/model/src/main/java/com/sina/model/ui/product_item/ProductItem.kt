package com.sina.model.ui.product_item

import com.sina.model.data.product_dto.Image

data class ProductItem(
    val id: Int?,
    val name: String,
    val description: String?,
    val price: String?,
    val images: List<Image>,
)