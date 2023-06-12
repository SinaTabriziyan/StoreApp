package com.sina.data_item.remote

import com.sina.model.mapper.mapProductDtoItemToProductItem
import com.sina.model.ui.product_item.ProductItem
import com.sina.network.services.products.ProductsService

class ItemRemoteDataSourceImpl(private val productsService: ProductsService) : ItemRemoteDataSource {
    override suspend fun getItem(productId: Int): List<ProductItem> =
        productsService.getItem(productId).map { mapProductDtoItemToProductItem(it) }
}