package com.sina.domain_main.usecase

import com.sina.common.responsestate.open
import com.sina.domain_main.interactor.UseCaseFlow
import com.sina.domain_main.repository.CategoryRepository
import com.sina.model.ui.category_item.CategoryItem
import kotlinx.coroutines.flow.Flow

class CategoryUseCase (private val categoryRepository: CategoryRepository) :
    UseCaseFlow<CategoryUseCase.Params, List<CategoryItem>>() {

    data class Params(
        val page: Int,
        val orderBy: String,
    )

    override fun execute(params: Params): Flow<List<CategoryItem>> =
        with(params){categoryRepository.getTopRatedProducts(page,orderBy)}.open()
}