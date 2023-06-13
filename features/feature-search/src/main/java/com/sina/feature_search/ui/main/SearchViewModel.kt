package com.sina.feature_search.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.SearchProductsUseCase
import com.sina.model.ui.products_item.ProductsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchProductsUseCase: SearchProductsUseCase) : ViewModel() {

    private val _productsBySearch = MutableStateFlow<List<ProductsItem>>(emptyList())
    val productsBySearch: StateFlow<List<ProductsItem>> = _productsBySearch.asStateFlow()

    fun getProductsBySearch(searchQuery: String) {
        viewModelScope.launch {
            searchProductsUseCase.invoke(SearchProductsUseCase.Params(1, searchQuery)).collectLatest {
                when (it) {
                    is InteractState.Error -> {}
                    is InteractState.Loading -> {}
                    is InteractState.Success -> {
                        _productsBySearch.value = it.data
                    }
                }
            }
        }

    }
}