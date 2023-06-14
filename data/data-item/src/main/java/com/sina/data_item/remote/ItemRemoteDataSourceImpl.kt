package com.sina.data_item.remote

import com.sina.model.mapper.mapProductDtoItemToProductItem
import com.sina.model.ui.product_item.ProductItem
import com.sina.network.services.products.StoreServices

class ItemRemoteDataSourceImpl(private val storeServices: StoreServices) : ItemRemoteDataSource {
    override suspend fun getItem(productId: Int): List<ProductItem> =
        storeServices.getItem(productId).map { mapProductDtoItemToProductItem(it) }
}