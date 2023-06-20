package com.sina.feature_home

import android.content.Context
import androidx.datastore.dataStore
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.ProductsByCategoryUseCase
import com.sina.domain_main.usecase.ProductsUseCase
import com.sina.local.data.datastore.AppDataStore
import com.sina.model.ui.products_item.ProductsItem
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    productsUseCase: ProductsUseCase,
    productsByCategoryUseCase: ProductsByCategoryUseCase,
    private val dataStore: AppDataStore
) : BaseViewModel() {
    private val TAG = "HomeViewModel"
    private val listItem: Array<MainProducts> = Array(3) { MainProducts.createData("", emptyList()) }
    private val topRatedProductsParams = ProductsUseCase.Params(1, "rating")
    private val latestProductsParams = ProductsUseCase.Params(1, "date")
    private val mostProductsParams = ProductsUseCase.Params(1, "popularity")
    private val sliderProductsParams = ProductsByCategoryUseCase.Params(1, "119")

    val readBackOnline = dataStore.readBackOnline.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), false
    )
    private val _allMainProducts = MutableStateFlow<List<MainProducts>>(emptyList())
    val allMainProducts: StateFlow<List<MainProducts>> = _allMainProducts

    private val _sliderImages = MutableStateFlow<List<ProductsItem.Image>>(emptyList())
    val sliderImages: StateFlow<List<ProductsItem.Image>> = _sliderImages

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

    private val sliderProducts: StateFlow<InteractState<List<ProductsItem>>> =
        productsByCategoryUseCase.invoke(sliderProductsParams).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), InteractState.Loading
        )

    private fun StateFlow<InteractState<List<ProductsItem>>>.expand(title: String, index: Int) {
        viewModelScope.launch {
            collectLatest {
                when (it) {
                    is InteractState.Error -> {
                        uiState.value = UiState.Error

                        Timber.e("Error ${it.errorMessage}")
                    }

                    is InteractState.Loading -> {
                        uiState.value = UiState.Loading
                    }

                    is InteractState.Success -> {
                        listItem[index] = MainProducts.createData(title, it.data)
                        _allMainProducts.value = listItem.toList()
                        uiState.value = UiState.Success
                    }
                }
            }
        }
    }

    fun saveBackOnline(status: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStore.saveBackOnline(status)
    }

    init {
        latestProducts.expand("جدیدترین", 0)
        topRatedProducts.expand("پربازدیدترین", 1)
        mostProducts.expand("بهترین", 2)
        sliderProducts.expand {
            val images = it[0].images
            if (images != null) _sliderImages.value = images
        }
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