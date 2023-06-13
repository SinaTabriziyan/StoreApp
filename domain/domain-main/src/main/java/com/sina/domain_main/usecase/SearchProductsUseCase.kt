package com.sina.domain_main.usecase

import com.sina.common.responsestate.open
import com.sina.domain_main.interactor.Interactor
import com.sina.domain_main.repository.SearchProductsRepository
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.flow.Flow

class SearchProductsUseCase(private val searchProductsRepository: SearchProductsRepository) :
    Interactor<SearchProductsUseCase.Params, List<ProductsItem>>() {
    data class Params(val page: Int, val searchQuery: String)

    override fun doWork(params: Params): Flow<List<ProductsItem>> =
        with(params) { searchProductsRepository.getProductsBySearch(page, searchQuery) }.open()
}