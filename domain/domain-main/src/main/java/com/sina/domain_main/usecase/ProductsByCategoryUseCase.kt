package com.sina.domain_main.usecase

import com.sina.common.responsestate.open
import com.sina.domain_main.interactor.Interactor
import com.sina.domain_main.repository.ProductsRepository
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.flow.Flow

class ProductsByCategoryUseCase(private val productsRepository: ProductsRepository) :
    Interactor<ProductsByCategoryUseCase.Params, List<ProductsItem>>() {
    data class Params(val page: Int, val category: String)

    override fun doWork(params: Params): Flow<List<ProductsItem>> {
        return with(params) { productsRepository.getProductsByCategory(page, category) }.open()
    }
}