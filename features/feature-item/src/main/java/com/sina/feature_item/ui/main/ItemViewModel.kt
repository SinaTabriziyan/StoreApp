package com.sina.feature_item.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.ItemUseCase
import com.sina.model.ui.product_item.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
@HiltViewModel
class ItemViewModel @Inject constructor(itemUseCase: ItemUseCase, savedStateHandle: SavedStateHandle) : ViewModel() {
    private lateinit var productParams: ItemUseCase.Params

    init {
        val id = savedStateHandle.get<Int>("productId")
        if (id != null) productParams = ItemUseCase.Params(id)
    }


    val productItem: StateFlow<InteractState<List<ProductItem>>> =
        itemUseCase(productParams).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            InteractState.Loading
        )
}