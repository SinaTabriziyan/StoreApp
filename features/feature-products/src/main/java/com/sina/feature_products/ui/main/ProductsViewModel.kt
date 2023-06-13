package com.sina.feature_products.ui.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.ProductsByCategoryUseCase
import com.sina.model.ui.products_item.ProductsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsByCategoryUseCase: ProductsByCategoryUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val productsItems = arrayListOf<ProductsItem>()
    private var categoryId by Delegates.notNull<String>()
    private var page = 1

    init {
        Log.e("TAG", "33333: ")
        val id = savedStateHandle.get<Int>("categoryId")
        if (id != null) {
            getProductsById(id)
            getProducts(categoryId)
        }
    }

    fun nextPage() {
        page++
        getProducts(categoryId)
    }

    private fun getProductsById(id: Int) {
        categoryId = id.toString()
    }

    private val _products = MutableStateFlow<List<ProductsItem>>(emptyList())
    val products: StateFlow<List<ProductsItem>> = _products.asStateFlow()

    val productsByCategory =
        productsByCategoryUseCase(ProductsByCategoryUseCase.Params(page, categoryId)).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            InteractState.Loading
        )

    fun getProducts(categoryId: String) {
        viewModelScope.launch {
            productsByCategoryUseCase(ProductsByCategoryUseCase.Params(page, categoryId)).collect {
                when (it) {
                    is InteractState.Error -> {
                        Log.e("TAG", "getProducts: ", )}
                    is InteractState.Loading -> {
                        Log.e("TAG", "getProducts: ", )}
                    is InteractState.Success -> {
                        addProductsItems(it.data)
                        _products.value = productsItems
                    }
                }
            }
        }
    }

    private fun addProductsItems(data: List<ProductsItem>) {
        Log.e("TAG", "addProductsItems: ${productsItems.size}", )
        productsItems.addAll(data)
    }
}