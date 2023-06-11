package com.sina.domain_main.usecase.interactor

import android.util.Log
import com.sina.common.responsestate.ResponseState
import com.sina.domain_main.repository.ProductsRepository
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.flow.Flow

class ProductsUseCase(
    private val productsRepository: ProductsRepository,
) : Interactor<ProductsUseCase.Params, ResponseState<List<ProductsItem>>>() {

    data class Params(
        val page: Int,
    )

    override fun doWork(params: Params): Flow<ResponseState<List<ProductsItem>>> {
        return with(params) { productsRepository.getTopRatedProducts(page) }
    }


}