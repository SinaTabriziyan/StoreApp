package com.sina.data_search.remote

import com.sina.model.ui.products_item.ProductsItem

interface SearchProductsRemoteDataSource {
    suspend fun getProductsBySearch(page: Int, querySearch: String): List<ProductsItem>

}