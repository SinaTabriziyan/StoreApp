package com.sina.model.ui.products_item

import com.google.gson.annotations.SerializedName
import com.sina.model.data.products_dto.Image

data class ProductsItem(
    val id: Int?,
    val images: List<Image?>?,
    val name: String?,
    val price: String?,
) {

}