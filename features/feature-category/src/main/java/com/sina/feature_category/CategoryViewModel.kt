package com.sina.feature_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.common.responsestate.ResponseState
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.CategoryUseCase
import com.sina.model.ui.category_item.CategoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryUseCase: CategoryUseCase) : ViewModel() {
    private val TAG = "CategoryViewModel"

    private val categoriseProductsParams = CategoryUseCase.Params(1, "")
    val categoriseProductsList = categoryUseCase(categoriseProductsParams).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000),
        InteractState.Loading
    )
    private val _categoryProducts = MutableStateFlow<List<CategoryItem>>(emptyList())
    val categoryProducts: StateFlow<List<CategoryItem>> = _categoryProducts

    init {
        getCategoriseProductsList(categoriseProductsParams)
    }

    private fun getCategoriseProductsList(categoriseProductsParams: CategoryUseCase.Params) {
        viewModelScope.launch {
//            categoryUseCase(categoriseProductsParams).collect { state ->
//                when (state) {
//                    is InteractState.Error -> {}
//                    is InteractState.Loading -> {state}
//                    is InteractState.Success -> {
//                        _categoryProducts.value = state.data
//                    }
//                }
//            }
            categoriseProductsList.collect {
                when (it) {
                    is InteractState.Error ->{}
                    is InteractState.Loading ->{}
                    is InteractState.Success -> _categoryProducts.value = it.data
                }
            }
        }
    }
}