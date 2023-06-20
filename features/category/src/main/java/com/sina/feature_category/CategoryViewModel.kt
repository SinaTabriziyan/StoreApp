package com.sina.feature_category

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.common.responsestate.ResponseState
import com.sina.domain_main.interactor.InteractState
import com.sina.domain_main.usecase.CategoryUseCase
import com.sina.local.data.datastore.AppDataStore
import com.sina.model.ui.category_item.CategoryItem
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase, dataStore: AppDataStore
) :
    BaseViewModel() {
    private val TAG = "CategoryViewModel"

    private val categoriseProductsParams = CategoryUseCase.Params(1, "")

    val categoriseProductsList: StateFlow<InteractState<List<CategoryItem>>> = categoryUseCase(categoriseProductsParams).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000),
        InteractState.Loading
    )
}