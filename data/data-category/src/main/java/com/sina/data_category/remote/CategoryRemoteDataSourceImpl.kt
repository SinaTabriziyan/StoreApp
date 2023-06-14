package com.sina.data_category.remote

import com.sina.model.mapper.mapCategoryDTOToCategory
import com.sina.model.ui.category_item.CategoryItem
import com.sina.network.services.products.StoreServices

class CategoryRemoteDataSourceImpl(private val storeServices: StoreServices) : CategoryRemoteDataSource {
    override suspend fun getCategoriesList(page: Int, orderBy: String): List<CategoryItem> =
        storeServices.getCategories().map { mapCategoryDTOToCategory(it) }
}