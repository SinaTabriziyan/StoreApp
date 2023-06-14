package com.sina.data_product.remote

import com.sina.model.mapper.mapProductsDtoItemToProductsItem
import com.sina.model.ui.products_item.ProductsItem
import com.sina.network.services.products.StoreServices

class ProductsRemoteDataSourceImpl(private val storeServices: StoreServices) : ProductsRemoteDataSource {
    override suspend fun getTopRatedProducts(page: Int, orderBy: String): List<ProductsItem> =
        storeServices.getProductsList(page, orderBy).map { mapProductsDtoItemToProductsItem(it) }

    override suspend fun getProductsByCategory(page: Int, orderBy: String): List<ProductsItem> {
        return storeServices.getProductsByCategory(page,orderBy).map { mapProductsDtoItemToProductsItem(it) }
    }
}