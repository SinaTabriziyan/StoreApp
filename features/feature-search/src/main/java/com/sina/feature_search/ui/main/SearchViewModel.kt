package com.sina.feature_search.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.SearchProductsUseCase
import com.sina.model.ui.products_item.ProductsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchProductsUseCase: SearchProductsUseCase) : ViewModel() {
    private val _productsBySearch = MutableStateFlow<List<ProductsItem>>(mutableListOf())
    val productsBySearch: StateFlow<List<ProductsItem>> = _productsBySearch.asStateFlow()
    private var job: Job? = null
    private var page = 1
    private lateinit var searchQuery: String

    fun nextPage() {
        page++
        getProductsBySearch(searchQuery)
    }

    fun getProductsBySearch(searchQuery: String) {
        this.searchQuery = searchQuery
        job?.cancel()
        job = viewModelScope.launch {
            Log.e("TAG", "getProductsBySearch: $page", )
            searchProductsUseCase.invoke(SearchProductsUseCase.Params(page, searchQuery)).collectLatest {
                when (it) {
                    is InteractState.Error -> {}
                    is InteractState.Loading -> {}
                    is InteractState.Success -> {
                        _productsBySearch.value += it.data
                    }
                }
            }
        }

    }
}