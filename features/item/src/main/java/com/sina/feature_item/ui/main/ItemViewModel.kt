package com.sina.feature_item.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.AddItemUseCase
import com.sina.domain_main.usecase.ItemUseCase
import com.sina.local.data.datastore.AppDataStore
import com.sina.model.ui.product_details_item.ProductDetails
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ItemViewModel @Inject constructor(
    itemUseCase: ItemUseCase,
    savedStateHandle: SavedStateHandle,
    dataStore: AppDataStore,
    private val addItemUseCase: AddItemUseCase,
) : BaseViewModel(dataStore) {
    private var itemId by Delegates.notNull<Int>()

    init {
        val id = savedStateHandle.get<Int>("productId")
        if (id != null) {
            getItemProduct(id)
        }
    }

    private val _itemAdded = MutableStateFlow<Boolean>(false)
    val itemAdded: StateFlow<Boolean> = _itemAdded.asStateFlow()

    private val _item = MutableStateFlow<InteractState<ProductDetails>>(InteractState.Loading)
    val item: StateFlow<InteractState<ProductDetails>> = _item.asStateFlow()
    private fun getItemProduct(id: Int) {
        itemId = id
    }

    fun addItemToCart(id: Int) {
        _itemAdded.value = true
        // TODO: check this id later
        viewModelScope.launch {
            addItemUseCase.invoke(AddItemUseCase.Params("sina", id, 1))
        }
    }

    val productDetails: StateFlow<InteractState<ProductDetails>> =
        itemUseCase(ItemUseCase.Params(itemId)).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            InteractState.Loading
        )
}