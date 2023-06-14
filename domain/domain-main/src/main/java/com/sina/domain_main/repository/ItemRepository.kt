package com.sina.domain_main.repository

import com.sina.common.responsestate.ResponseState
import com.sina.domain_main.usecase.AddItemUseCase
import com.sina.model.entity.ProductEntity
import com.sina.model.ui.product_item.ProductItem
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getProduct(productId: Int): Flow<ResponseState<List<ProductItem>>>
    suspend fun addProduct(productEntity: ProductEntity)

}