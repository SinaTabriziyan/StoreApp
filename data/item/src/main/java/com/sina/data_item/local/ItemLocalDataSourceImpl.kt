package com.sina.data_item.local

import com.sina.local.data.db.ProductDao
import com.sina.model.entity.ProductEntity
import com.sina.model.mapper.mapProductsDtoItemToProductsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ItemLocalDataSourceImpl(private val productDao: ProductDao) : ItemLocalDataSource {
    override suspend fun addItemLocal(productEntity: ProductEntity) = productDao.insertItemLocal(productEntity)
    override  fun getItemsLocal(): Flow<List<ProductEntity>> = productDao.getItemsLocal()
}