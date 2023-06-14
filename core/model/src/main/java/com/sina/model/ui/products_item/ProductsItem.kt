package com.sina.model.ui.products_item


data class ProductsItem(
    val id: Int?,
    val averageRating: String?,
    val images: List<Image>?,
    val name: String?,
    val price: String?,
    val regularPrice : String?,
    val salePrice : String?
) {
    data class Image(
        val id: Int?,
        val name: String?,
        val src: String?
    )
}