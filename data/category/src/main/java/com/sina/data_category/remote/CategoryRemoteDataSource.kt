package com.sina.data_category.remote

import com.sina.model.ui.category_item.CategoryItem

interface CategoryRemoteDataSource {

    suspend fun getCategoriesList(page: Int,orderBy:String): List<CategoryItem>

}
