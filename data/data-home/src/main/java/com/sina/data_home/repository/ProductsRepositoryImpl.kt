package com.sina.data_home.repository

import com.sina.common.responsestate.ResponseState
import com.sina.common.responsestate.asResult
import com.sina.data_home.remote.ProductsRemoteDataSource
import com.sina.domain_main.repository.ProductsRepository
import com.sina.model.ui.products_item.ProductsItem
import com.sina.network.annotation.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductsRepositoryImpl(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : ProductsRepository {
    override fun getTopRatedProducts(page: Int, orderBy: String): Flow<ResponseState<List<ProductsItem>>> =
        flow { emit(productsRemoteDataSource.getTopRatedProducts(page, orderBy)) }.flowOn(dispatcher).asResult()

}