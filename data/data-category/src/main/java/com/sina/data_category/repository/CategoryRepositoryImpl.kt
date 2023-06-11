package com.sina.data_category.repository

import com.sina.common.responsestate.ResponseState
import com.sina.common.responsestate.asResult
import com.sina.data_category.remote.CategoryRemoteDataSource
import com.sina.domain_main.repository.CategoryRepository
import com.sina.model.ui.category_item.CategoryItem
import com.sina.network.annotation.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategoryRepositoryImpl(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : CategoryRepository {
    override fun getTopRatedProducts(page: Int, orderBy: String): Flow<ResponseState<List<CategoryItem>>> {
        return flow { emit(categoryRemoteDataSource.getCategoriesList(page, orderBy)) }.asResult().flowOn(dispatcher)
    }
}