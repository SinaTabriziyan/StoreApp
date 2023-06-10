package com.sina.data_home.remote

import com.sina.model.mapper.mapProductsDtoItemToProductsItem
import com.sina.model.ui.products_item.ProductsItem
import com.sina.network.services.products.ProductsService
import javax.inject.Inject

class ProductsRemoteDataSourceImpl @Inject constructor(
    private val productsService: ProductsService,
) : ProductsRemoteDataSource {
    override suspend fun getVisitedProducts() =
        productsService.getTopRatedProductsService().map { mapProductsDtoItemToProductsItem(it) }

    override suspend fun getLatestProducts(): List<ProductsItem> =
        productsService.getLatestProducts().map { mapProductsDtoItemToProductsItem(it) }

    override suspend fun getTopRatedProducts(page: Int, orderBy: String): List<ProductsItem> =
        productsService.getTopRatedProductsService(page,orderBy).map { mapProductsDtoItemToProductsItem(it) }
}