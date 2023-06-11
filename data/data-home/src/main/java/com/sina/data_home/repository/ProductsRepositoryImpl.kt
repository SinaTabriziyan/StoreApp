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
import javax.inject.Inject

class ProductsRepositoryImpl (
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : ProductsRepository {
    override suspend fun getLatestProducts(): Flow<List<ProductsItem>> =
        flow { emit(productsRemoteDataSource.getLatestProducts()) }.flowOn(dispatcher)

    override suspend fun getMostProducts(): Flow<List<ProductsItem>> =
        flow { emit(productsRemoteDataSource.getVisitedProducts()) }.flowOn(dispatcher)

    override suspend fun getTopRatedProducts(page:Int,orderBy:String): Flow<List<ProductsItem>> =
        flow { emit(productsRemoteDataSource.getTopRatedProducts(page,orderBy)) }.flowOn(dispatcher)

}