package com.sina.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.ProductsByCategoryUseCase
import com.sina.domain_main.usecase.ProductsUseCase
import com.sina.model.ui.products_item.ProductsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    productsUseCase: ProductsUseCase,
    productsByCategoryUseCase: ProductsByCategoryUseCase,
) : ViewModel() {
    private val TAG = "HomeViewModel"
    private val listItem: Array<MainProducts> = Array(3) { MainProducts.createData("", emptyList()) }
    private val _allMainProducts = MutableStateFlow<List<MainProducts>>(emptyList())
    private val topRatedProductsParams = ProductsUseCase.Params(1, "rating")
    private val latestProductsParams = ProductsUseCase.Params(1, "date")
    private val mostProductsParams = ProductsUseCase.Params(1, "popularity")
    private val sliderProductsParams = ProductsByCategoryUseCase.Params(1, "119")

    val allMainProducts: StateFlow<List<MainProducts>> = _allMainProducts

    private val topRatedProducts: StateFlow<InteractState<List<ProductsItem>>> =
        productsUseCase(topRatedProductsParams).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), InteractState.Loading
        )
    private val latestProducts: StateFlow<InteractState<List<ProductsItem>>> =
        productsUseCase(latestProductsParams).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), InteractState.Loading
        )
    private val mostProducts: StateFlow<InteractState<List<ProductsItem>>> =
        productsUseCase(mostProductsParams).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), InteractState.Loading
        )

    val sliderProducts =
        productsByCategoryUseCase.invoke(sliderProductsParams).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), InteractState.Loading
        )

    private fun StateFlow<InteractState<List<ProductsItem>>>.open(title: String, index: Int) {
        viewModelScope.launch {
            collectLatest {
                when (it) {
                    is InteractState.Error -> {}
                    is InteractState.Loading -> {}
                    is InteractState.Success -> {
                        listItem[index] = MainProducts.createData(title, it.data)
                        _allMainProducts.value = listItem.toList()
                    }
                }
            }
        }
    }

    init {
        latestProducts.open("جدیدترین", 0)
        topRatedProducts.open("پربازدیدترین", 1)
        mostProducts.open("بهترین", 2)
    }

    data class MainProducts(
        val title: String,
        val products: List<ProductsItem>,
    ) {
        companion object {
            fun createData(
                title: String,
                products: List<ProductsItem>,
            ) = MainProducts(title, products)
        }
    }
}