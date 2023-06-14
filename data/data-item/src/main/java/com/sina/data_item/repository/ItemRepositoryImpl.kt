package com.sina.data_item.repository

import com.sina.common.responsestate.ResponseState
import com.sina.common.responsestate.asResult
import com.sina.data_item.local.ItemLocalDataSource
import com.sina.data_item.remote.ItemRemoteDataSource
import com.sina.domain_main.repository.ItemRepository
import com.sina.model.entity.ProductEntity
import com.sina.model.ui.product_item.ProductItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ItemRepositoryImpl(
    private val itemRemoteDataSource: ItemRemoteDataSource,
    private val itemLocalDataSource: ItemLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
) : ItemRepository {
    override fun getProductDetails(productId: Int): Flow<ResponseState<List<ProductItem>>> =
        flow { emit(itemRemoteDataSource.getProductDetails(productId)) }.asResult().flowOn(dispatcher)

    override suspend fun addProduct(productEntity:ProductEntity){
        itemLocalDataSource.addItem(productEntity)
    }

}