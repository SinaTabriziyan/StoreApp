package com.sina.model.mapper

import com.sina.model.network.product_details_dto.ProductDetailsDto
import com.sina.model.ui.product_details_item.ProductDetails

fun mapProductDetailsDtoToProductDetails(dto: ProductDetailsDto): ProductDetails = with(dto) {
    ProductDetails(
        id,
        name,
        description,
        price,
        regularPrice,
        salePrice,
        averageRating,
        categories.map { ProductDetails.Category(it.id, it.name) },
        images.map { ProductDetails.Image(it.id, it.name, it.src) },
        ratingCount,
        shortDescription,
        tags.map { ProductDetails.Tag(it.id, it.name) },
        relatedIds,
        weight,
        ProductDetails.Dimensions(dimensions.height, dimensions.length, dimensions.width)
    )
}