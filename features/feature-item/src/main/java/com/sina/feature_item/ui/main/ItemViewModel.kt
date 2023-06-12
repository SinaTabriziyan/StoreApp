package com.sina.feature_item.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.ItemUseCase
import com.sina.model.ui.product_item.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemUseCase: ItemUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var itemId by Delegates.notNull<Int>()
    init {
        val id = savedStateHandle.get<Int>("productId")
        if (id != null) {
            getItemProduct(id)
        }
    }
    private fun getItemProduct(id: Int) {
        itemId = id
    }
    val productItem: StateFlow<InteractState<List<ProductItem>>> =
        itemUseCase(ItemUseCase.Params(itemId)).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            InteractState.Loading
        )
}