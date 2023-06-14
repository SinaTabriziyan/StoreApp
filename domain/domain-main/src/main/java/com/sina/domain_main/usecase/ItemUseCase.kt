package com.sina.domain_main.usecase

import com.sina.common.responsestate.open
import com.sina.domain_main.interactor.UseCaseFlow
import com.sina.domain_main.repository.ItemRepository
import com.sina.model.ui.product_item.ProductItem
import kotlinx.coroutines.flow.Flow

class ItemUseCase(private val itemRepository: ItemRepository) :
    UseCaseFlow<ItemUseCase.Params, List<ProductItem>>() {

    data class Params(val productId: Int)

    override fun execute(params: Params): Flow<List<ProductItem>> =
        with(params) { itemRepository.getProduct(productId) }.open()
}