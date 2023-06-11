package com.sina.domain_main.repository

import com.sina.common.responsestate.ResponseState
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getTopRatedProducts(page:Int): Flow<ResponseState<List<ProductsItem>>>
}