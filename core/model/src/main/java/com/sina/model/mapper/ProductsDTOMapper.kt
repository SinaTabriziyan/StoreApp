package com.sina.model.mapper

import com.sina.model.data.products_dto.ProductsDTOItem
import com.sina.model.ui.products_item.ProductsItem

fun mapProductsDtoItemToProductsItem(productsDTOItem: ProductsDTOItem): ProductsItem {
    return ProductsItem(
        id = productsDTOItem.id,
        images = productsDTOItem.images,
        name = productsDTOItem.name,
        price = productsDTOItem.price
    )
}
