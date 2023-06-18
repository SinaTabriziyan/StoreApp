package com.sina.data_product.remote

import com.sina.model.ui.products_item.ProductsItem

interface ProductsRemoteDataSource {
    suspend fun getTopRatedProducts(page: Int,orderBy:String): List<ProductsItem>
    suspend fun getProductsByCategory(page: Int,orderBy:String): List<ProductsItem>
}