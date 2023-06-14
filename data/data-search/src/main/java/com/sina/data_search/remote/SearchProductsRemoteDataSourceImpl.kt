package com.sina.data_search.remote

import com.sina.model.mapper.mapProductsDtoItemToProductsItem
import com.sina.model.ui.products_item.ProductsItem
import com.sina.network.services.products.StoreServices

class SearchProductsRemoteDataSourceImpl(private val storeServices: StoreServices) : SearchProductsRemoteDataSource {
    override suspend fun getProductsBySearch(page: Int, querySearch: String): List<ProductsItem> =
        storeServices.getProductsBySearch(page, querySearch).map { mapProductsDtoItemToProductsItem(it) }
}