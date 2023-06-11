package com.sina.domain_main.repository

import com.sina.common.responsestate.ResponseState
import com.sina.model.ui.category_item.CategoryItem
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getTopRatedProducts(page: Int, orderBy: String): Flow<ResponseState<List<CategoryItem>>>
}