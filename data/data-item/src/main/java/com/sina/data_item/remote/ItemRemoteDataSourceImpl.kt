package com.sina.data_item.remote

import com.sina.model.mapper.mapProductDetailsDtoToProductDetails
import com.sina.model.ui.product_details_item.ProductDetails
import com.sina.network.services.products.StoreServices

class ItemRemoteDataSourceImpl(private val storeServices: StoreServices) : ItemRemoteDataSource {
    override suspend fun getProductDetails(productId: Int): ProductDetails =
        mapProductDetailsDtoToProductDetails(storeServices.getProductDetails(productId))
}