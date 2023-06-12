package com.sina.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
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
class HomeViewModel @Inject constructor(productsUseCase: ProductsUseCase) : ViewModel() {
    private val TAG = "HomeViewModel"
    private val listItem: Array<MainProducts> = Array(3) { MainProducts.createData("", emptyList()) }
    private val _allMainProducts = MutableStateFlow<List<MainProducts>>(emptyList())
    private val topRatedProductsParams = ProductsUseCase.Params(1, "rating")
    private val latestProductsParams = ProductsUseCase.Params(1, "date")
    private val mostProductsParams = ProductsUseCase.Params(1, "popularity")
    val allMainProducts: StateFlow<List<MainProducts>> = _allMainProducts

    private val topRatedProducts: StateFlow<InteractState<List<ProductsItem>>> =
        productsUseCase(topRatedProductsParams).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            InteractState.Loading
        )
    private val latestProducts: StateFlow<InteractState<List<ProductsItem>>> =
        productsUseCase(latestProductsParams).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            InteractState.Loading
        )
    private val mostProducts: StateFlow<InteractState<List<ProductsItem>>> =
        productsUseCase(mostProductsParams).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            InteractState.Loading
        )


    fun StateFlow<InteractState<List<ProductsItem>>>.open(title: String, index: Int) {
        viewModelScope.launch {
            this@open.collectLatest { it ->
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
        latestProducts.open("a", 0)
        topRatedProducts.open("b", 1)
        mostProducts.open("c", 2)
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