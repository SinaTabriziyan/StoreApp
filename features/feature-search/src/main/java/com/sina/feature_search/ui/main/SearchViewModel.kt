package com.sina.feature_search.ui.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_BY_TYPE
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_BY_TYPE_ID
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_TYPE
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_TYPE_ID
import com.sina.common.constants.Constants.Companion.ORDER
import com.sina.common.constants.Constants.Companion.ORDER_BY
import com.sina.common.constants.Constants.Companion.SEARCH
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.SearchProductsUseCase
import com.sina.local.data.datastore.AppDataStore
import com.sina.model.ui.products_item.ProductsItem
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val dataStore: AppDataStore,
    private val savedStateHandle: SavedStateHandle
) :
    BaseViewModel() {

    var searchOrderType = DEFAULT_SEARCH_ORDER_TYPE
    var searchOrderTypeId = DEFAULT_SEARCH_ORDER_TYPE_ID
    var searchOrderByType = DEFAULT_SEARCH_ORDER_BY_TYPE
    var searchOrderByTypeId = DEFAULT_SEARCH_ORDER_BY_TYPE_ID

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

    fun applyQueriesQueries() = hashMapOf<String, String>().apply {
        viewModelScope.launch {
            readSearchType.collect { values ->
                searchOrderType = values.selectedSearchOrderType
                searchOrderTypeId = values.selectedSearchOrderTypeId
                searchOrderByType = values.selectedSearchOrderByType
                searchOrderByTypeId = values.selectedSearchOrderByTypeId
            }
        }
        Timber.e("Filters:-->searchQuery:$searchQuery,searchOrderByType:$searchOrderByType,searchOrderType:$searchOrderType")
        put(SEARCH, searchQuery)
        put(ORDER_BY, searchOrderByType)
        put(ORDER, searchOrderType)
    }

    fun getProductsBySearch(searchQuery: String) {
        this.searchQuery = searchQuery
        job?.cancel()
        job = viewModelScope.launch {
            Log.e("TAG", "getProductsBySearch: $page")
            searchProductsUseCase.invoke(SearchProductsUseCase.Params(page, searchQuery, applyQueriesQueries())).collectLatest {
                when (it) {
                    is InteractState.Error -> Timber.d(it.errorMessage)
                    is InteractState.Loading -> uiState.value = UiState.Loading
                    is InteractState.Success -> {
                        uiState.value = UiState.Success
                        _productsBySearch.value += it.data
                    }
                }
            }
        }

    }

    fun saveSearchOrderType(searchOrderTypeChip: String, searchOrderTypeIdChip: Int) = viewModelScope.launch {
        dataStore.saveSearchOrderType(searchOrderTypeChip, searchOrderTypeIdChip)
    }
}