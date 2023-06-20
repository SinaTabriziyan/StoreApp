package com.sina.feature_item.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.AddItemUseCase
import com.sina.domain_main.usecase.GetItemUseCase
import com.sina.local.data.datastore.AppDataStore
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase,
    savedStateHandle: SavedStateHandle,
    dataStore: AppDataStore,
    private val addItemUseCase: AddItemUseCase,
) : BaseViewModel() {
    private var itemId by Delegates.notNull<Int>()

    init {
        savedStateHandle.get<Int>("productId").also {
            if (it != null) itemId = it
        }
//        val id = savedStateHandle.get<Int>("productId")
//        if (id != null) itemId = id
    }

    private val _itemAdded = MutableStateFlow<Boolean>(false)
    val itemAdded: StateFlow<Boolean> = _itemAdded.asStateFlow()
//
//    private val _item = MutableStateFlow<InteractState<ProductDetails>>(InteractState.Loading)
//    val item: StateFlow<InteractState<ProductDetails>> = _item.asStateFlow()

    fun addItemToCart(id: Int) {
        _itemAdded.value = true
        // TODO: check this id later
        viewModelScope.launch {
            addItemUseCase.invoke(AddItemUseCase.Params("sina", id, 1))
        }
    }

    val productDetails =
        getItemUseCase(GetItemUseCase.Params(itemId)).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            InteractState.Loading
        )

}