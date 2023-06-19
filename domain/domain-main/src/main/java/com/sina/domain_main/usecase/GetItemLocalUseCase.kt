package com.sina.domain_main.usecase

import com.sina.domain_main.interactor.UseCaseFlow
import com.sina.domain_main.repository.ItemRepository
import com.sina.model.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

class GetItemLocalUseCase(private val itemRepository: ItemRepository) {

    fun getItemsLocal() = itemRepository.getItemsLocal()
}