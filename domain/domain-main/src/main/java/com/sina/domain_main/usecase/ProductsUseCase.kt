package com.sina.domain_main.usecase

import com.sina.common.responsestate.ResponseState
import com.sina.domain_main.repository.ProductsRepository
import com.sina.domain_main.interactor.Interactor
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.flow.Flow

class ProductsUseCase(private val productsRepository: ProductsRepository) :
    Interactor<ProductsUseCase.Params, ResponseState<List<ProductsItem>>>() {

    data class Params(
        val page: Int,
        val orderBy: String,
    )

    override fun doWork(params: Params): Flow<ResponseState<List<ProductsItem>>> =
        with(params) { productsRepository.getTopRatedProducts(page, orderBy) }


}