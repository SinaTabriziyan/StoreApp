package com.sina.feature_home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.common.responsestate.ResponseState
import com.sina.domain_main.usecase.interactor.InteractState
import com.sina.domain_main.usecase.interactor.ProductsUseCase
import com.sina.model.ui.products_item.ProductsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productsUseCase: ProductsUseCase) : ViewModel() {
    private val TAG = "HomeViewModel"

    val topRatedProductsParams = ProductsUseCase.Params(1, "rating")
    val latestProductsParams = ProductsUseCase.Params(1, "date")
    val mostProductsParams = ProductsUseCase.Params(1, "popularity")

    val topRatedProducts = productsUseCase(topRatedProductsParams).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        InteractState.Loading
    )
    val latestProducts = productsUseCase(topRatedProductsParams).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        InteractState.Loading
    )
    val mostProducts = productsUseCase(topRatedProductsParams).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        InteractState.Loading
    )
    private val _allMainProducts = MutableStateFlow<ResponseState<List<ProductsItem>>>(ResponseState.Loading)
    val allMainProducts: StateFlow<ResponseState<List<ProductsItem>>> = _allMainProducts

    init {
        getProducts(topRatedProductsParams)
//        getProducts(productsParams)
//        getProducts(productsParams)
    }

    private fun getProducts(productsParams: ProductsUseCase.Params) {
        viewModelScope.launch {
            productsUseCase(productsParams).collect { response ->
                when (response) {
                    is InteractState.Error -> {
                        Log.e(TAG, "viewModel: getProducts: ${response.errorMessage}")
                    }
                    is InteractState.Loading -> {}
                    is InteractState.Success -> {
                        Log.e(TAG, "viewModel: getProducts: ${response.data}")
                        _allMainProducts.value = response.data
                    }
                }
            }
        }
    }
}