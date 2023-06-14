package com.sina.data_item.local

import com.sina.model.entity.ProductEntity

interface ItemLocalDataSource {
   suspend fun addItem(productEntity: ProductEntity)
}