package com.sina.data_item.remote

import com.sina.model.ui.product_item.ProductItem

interface ItemRemoteDataSource {
    suspend fun getItem(productId: Int): List<ProductItem>
}