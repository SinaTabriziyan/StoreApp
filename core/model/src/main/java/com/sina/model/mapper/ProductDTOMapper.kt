package com.sina.model.mapper

import com.sina.model.data.product_dto.ProductDTOItem
import com.sina.model.ui.product_item.ProductItem

fun mapProductDtoItemToProductItem(productDTOItem: ProductDTOItem): ProductItem {
    return ProductItem(
        id = productDTOItem.id,
        name = productDTOItem.name,
        description = productDTOItem.description,
        price = productDTOItem.price,
        images = productDTOItem.images
    )
}