package com.sina.data_item.local

import com.sina.local.data.db.ProductDao
import com.sina.model.entity.ProductEntity

class ItemLocalDataSourceImpl(private val productDao: ProductDao) : ItemLocalDataSource {
    override suspend fun addItem(productEntity: ProductEntity) {
        productDao.insert(productEntity)
    }
}