package com.sina.data_item.local

import com.sina.model.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ItemLocalDataSource {
    suspend fun addItemLocal(productEntity: ProductEntity)

    fun getItemsLocal(): Flow<List<ProductEntity>>
}