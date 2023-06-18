package com.sina.data_product.repository

import com.sina.common.responsestate.ResponseState
import com.sina.common.responsestate.asResult
import com.sina.data_product.remote.ProductsRemoteDataSource
import com.sina.domain_main.repository.ProductsRepository
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductsRepositoryImpl(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : ProductsRepository {
    override fun getTopRatedProducts(page: Int, orderBy: String): Flow<ResponseState<List<ProductsItem>>> =
        flow { emit(productsRemoteDataSource.getTopRatedProducts(page, orderBy)) }.asResult().flowOn(dispatcher)

    override fun getProductsByCategory(page: Int, category: String): Flow<ResponseState<List<ProductsItem>>> {
        return flow { emit(productsRemoteDataSource.getProductsByCategory(page, category)) }.asResult().flowOn(dispatcher)
    }

}