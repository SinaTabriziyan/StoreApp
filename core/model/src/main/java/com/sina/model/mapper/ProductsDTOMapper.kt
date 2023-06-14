package com.sina.model.mapper

import com.sina.model.data.products_dto.ProductsDTOItem
import com.sina.model.ui.products_item.ProductsItem

fun mapProductsDtoItemToProductsItem(dto: ProductsDTOItem): ProductsItem = with(dto) {
    ProductsItem(
        id,
        averageRating,
        images?.map { ProductsItem.Image(it?.id, it?.name, it?.src) }, name, price, regularPrice, salePrice
    )
}
