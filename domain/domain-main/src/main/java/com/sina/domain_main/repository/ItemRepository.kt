package com.sina.domain_main.repository

import com.sina.common.responsestate.ResponseState
import com.sina.model.entity.ProductEntity
import com.sina.model.ui.product_details_item.ProductDetails
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItemStream(productId: Int): Flow<ResponseState<ProductDetails>>
    suspend fun addItemLocal(productEntity: ProductEntity)
    fun getItemsLocal(): Flow<List<ProductEntity>>

}