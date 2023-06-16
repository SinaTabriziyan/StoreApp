package com.sina.domain_main.usecase

import com.sina.common.responsestate.open
import com.sina.domain_main.interactor.UseCaseFlow
import com.sina.domain_main.repository.SearchProductsRepository
import com.sina.model.ui.products_item.ProductsItem
import kotlinx.coroutines.flow.Flow

class SearchProductsUseCase(private val searchProductsRepository: SearchProductsRepository) :
    UseCaseFlow<SearchProductsUseCase.Params, List<ProductsItem>>() {
    data class Params(val page: Int, val searchQuery: String, val orderBy: String)

    override fun execute(params: Params): Flow<List<ProductsItem>> =
        with(params) { searchProductsRepository.getProductsBySearch(page, searchQuery, orderBy) }.open()
}