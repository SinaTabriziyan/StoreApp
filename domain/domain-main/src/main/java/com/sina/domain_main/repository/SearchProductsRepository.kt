package com.sina.domain_main.repository

import com.sina.common.responsestate.ResponseState
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.flow.Flow

interface SearchProductsRepository {
    fun getProductsBySearch(page: Int, searchQuery: String, orderBy: String): Flow<ResponseState<List<ProductsItem>>>
}