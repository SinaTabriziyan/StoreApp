package com.sina.feature_cart

import androidx.lifecycle.viewModelScope
import com.sina.domain_main.usecase.GetItemLocalUseCase
import com.sina.local.data.datastore.AppDataStore
import com.sina.model.entity.ProductEntity
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(datastore: AppDataStore, private val getItemLocalUseCase: GetItemLocalUseCase) :
    BaseViewModel(datastore) {

    val getItemsLocal = getItemLocalUseCase.getItemsLocal().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(1_000), emptyList<ProductEntity>()
    )

}