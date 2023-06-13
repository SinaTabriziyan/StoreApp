package com.sina.data_search.repository

import com.sina.common.responsestate.ResponseState
import com.sina.common.responsestate.asResult
import com.sina.data_search.remote.SearchProductsRemoteDataSource
import com.sina.domain_main.repository.SearchProductsRepository
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchProductsRepositoryImpl(
    private val searchProductsRemoteDataSource: SearchProductsRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
): SearchProductsRepository {
    override fun getProductsBySearch(
        page: Int,
        searchQuery: String,
    ): Flow<ResponseState<List<ProductsItem>>> =
        flow { emit(searchProductsRemoteDataSource.getProductsBySearch(page,searchQuery)) }.asResult().flowOn(dispatcher)

}