package com.sina.domain_main.usecase.interactor

import com.sina.domain_main.repository.ProductsRepository
import com.sina.model.ui.products_item.ProductsItem
import com.sina.network.annotation.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProductsUseCase(
    private val productsRepository: ProductsRepository, @IODispatcher private val dispatcher: CoroutineDispatcher,
) : Interactor<ProductsUseCase.Params, List<ProductsItem>>() {

    override suspend fun doWork(params: Params): Flow<List<ProductsItem>> = withContext(dispatcher) {
        with(params) { productsRepository.getTopRatedProducts(page, orderBy) }
    }


    data class Params(
        val page: Int,
        val orderBy: String,
    )

}