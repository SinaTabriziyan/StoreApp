package com.sina.data_home.remote

import com.sina.model.ui.products_item.ProductsItem

interface ProductsRemoteDataSource {
    suspend fun getTopRatedProducts(page: Int,orderBy:String): List<ProductsItem>
}