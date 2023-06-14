package com.sina.data_item.remote

import com.sina.common.responsestate.ResponseState
import com.sina.model.ui.product_item.ProductItem

interface ItemRemoteDataSource {
    suspend fun getProductDetails(productId: Int): List<ProductItem>
}