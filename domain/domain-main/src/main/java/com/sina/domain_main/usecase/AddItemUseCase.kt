package com.sina.domain_main.usecase

import com.sina.domain_main.interactor.UseCase
import com.sina.domain_main.repository.ItemRepository
import com.sina.model.entity.ProductEntity

class AddItemUseCase(private val itemRepository: ItemRepository) : UseCase<AddItemUseCase.Params, Unit>() {
    data class Params(val userName: String, val productId: Int, val productNumber: Int)

    override suspend fun execute(params: Params) {
        with(params) { itemRepository.addProduct(ProductEntity(userName, productId, productNumber)) }
    }
}