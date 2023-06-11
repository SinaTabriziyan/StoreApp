package com.sina.data_category.remote

import com.sina.model.mapper.mapCategoryDTOToCategory
import com.sina.model.ui.category_item.CategoryItem
import com.sina.network.services.products.ProductsService

class CategoryRemoteDataSourceImpl(private val productsService: ProductsService) : CategoryRemoteDataSource {
    override suspend fun getCategoriesList(page: Int, orderBy: String): List<CategoryItem> =
        productsService.getCategories().map { mapCategoryDTOToCategory(it) }
}