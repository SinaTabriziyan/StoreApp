package com.sina.domain_main.usecase

import com.sina.common.responsestate.open
import com.sina.domain_main.repository.ProductsRepository
import com.sina.domain_main.interactor.UseCaseFlow
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.flow.Flow

class ProductsUseCase(private val productsRepository: ProductsRepository) :
    UseCaseFlow<ProductsUseCase.Params, List<ProductsItem>>() {

    data class Params(
        val page: Int,
        val orderBy: String,
    )

    override fun execute(params: Params): Flow<List<ProductsItem>> =
        with(params) { productsRepository.getTopRatedProducts(page, orderBy) }.open()
}