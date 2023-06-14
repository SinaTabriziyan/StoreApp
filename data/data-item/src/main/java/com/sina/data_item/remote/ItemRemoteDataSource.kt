package com.sina.data_item.remote

import com.sina.model.ui.product_details_item.ProductDetails

interface ItemRemoteDataSource {
    suspend fun getProductDetails(productId: Int): ProductDetails
}