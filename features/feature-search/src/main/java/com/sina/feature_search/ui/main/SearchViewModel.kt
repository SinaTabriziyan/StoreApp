package com.sina.feature_search.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_TYPE
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.SearchProductsUseCase
import com.sina.local.data.datastore.AppDataStore
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
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val dataStore: AppDataStore
) :
    ViewModel() {

    var searchType = DEFAULT_SEARCH_TYPE
    var networkStatus = false
    var backOnline = false

    private val _productsBySearch = MutableStateFlow<List<ProductsItem>>(mutableListOf())
    val productsBySearch: StateFlow<List<ProductsItem>> = _productsBySearch.asStateFlow()
    private var job: Job? = null
    private var page = 1
    private var searchQuery: String = ""

    val readSearchType = dataStore.readSearchType
    fun nextPage() {
        page++
        getProductsBySearch(searchQuery)
    }

//    fun applyQueries() = hashMapOf<String, String>().apply {
//        viewModelScope.launch {
//            readSearchType.collect { values ->
//                searchType = values.selectedSearchType
//            }
//        }
//        put(QUERY_TYPE, searchType)
//    }

    fun getSearchFilters() {
        viewModelScope.launch {
            readSearchType.collect { values ->
                searchType = values.selectedSearchType
            }
        }
    }

    fun getProductsBySearch(searchQuery: String) {
        this.searchQuery = searchQuery
        job?.cancel()
        job = viewModelScope.launch {
            Log.e("TAG", "getProductsBySearch: $page")
            searchProductsUseCase.invoke(SearchProductsUseCase.Params(page, searchQuery, searchType)).collectLatest {
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

    fun saveSearchType(
        searchTypeChip: String,
        searchTypeIdChip: Int,
        searchOrderTypeChip: String,
        searchOrderTypeIdChip: Int
    ) = viewModelScope.launch {
        dataStore.saveMealAndDietType(searchTypeChip, searchTypeIdChip, searchOrderTypeChip, searchOrderTypeIdChip)
        getSearchFilters()
        getProductsBySearch(searchQuery)
    }
}