package com.sina.domain_main.repository

import com.sina.common.responsestate.ResponseState
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getLatestProducts(): Flow<List<ProductsItem>>
    suspend fun getMostProducts(): Flow<List<ProductsItem>>
    suspend fun getTopRatedProducts(page:Int,orderBy:String): Flow<List<ProductsItem>>
}