package com.sina.domain_main.usecase

import com.sina.common.responsestate.ResponseState
import com.sina.domain_main.interactor.Interactor
import com.sina.domain_main.repository.CategoryRepository
import com.sina.model.ui.category_item.CategoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
class CategoryUseCase (private val categoryRepository: CategoryRepository) :
    Interactor<CategoryUseCase.Params, ResponseState<List<CategoryItem>>>() {

    data class Params(
        val page: Int,
        val orderBy: String,
    )

    override fun doWork(params: Params): Flow<ResponseState<List<CategoryItem>>> =
        with(params){categoryRepository.getTopRatedProducts(page,orderBy)}
}