package com.sina.domain_main.usecase

import com.sina.common.responsestate.open
import com.sina.domain_main.interactor.UseCaseFlow
import com.sina.domain_main.repository.ItemRepository
import com.sina.model.ui.product_details_item.ProductDetails
import kotlinx.coroutines.flow.Flow

class GetItemUseCase(private val itemRepository: ItemRepository) :
    UseCaseFlow<GetItemUseCase.Params, ProductDetails>() {
    data class Params(val productId: Int)
    override fun execute(params: Params): Flow<ProductDetails> = with(params) { itemRepository.getItemStream(productId) }.open()
}