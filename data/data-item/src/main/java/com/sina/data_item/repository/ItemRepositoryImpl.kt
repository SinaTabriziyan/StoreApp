package com.sina.data_item.repository

import com.sina.common.responsestate.ResponseState
import com.sina.common.responsestate.asResult
import com.sina.data_item.remote.ItemRemoteDataSource
import com.sina.domain_main.repository.ItemRepository
import com.sina.model.ui.product_item.ProductItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ItemRepositoryImpl(
    private val itemRemoteDataSource: ItemRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : ItemRepository {
    override fun getProduct(productId: Int): Flow<ResponseState<List<ProductItem>>> =
        flow { emit(itemRemoteDataSource.getItem(productId)) }.asResult().flowOn(dispatcher)
}