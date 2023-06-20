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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase, private val dataStore: AppDataStore
) :
    BaseViewModel() {
    private val TAG = "CategoryViewModel"

    val readBackOnline = dataStore.readBackOnline.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), false
    )
    private val _categoryList = MutableStateFlow<List<CategoryItem>>(emptyList())
    val categoryList: StateFlow<List<CategoryItem>> = _categoryList.asStateFlow()

    private val categoriseProductsParams = CategoryUseCase.Params(1, "")

    private val categoriseProductsList: StateFlow<InteractState<List<CategoryItem>>> = categoryUseCase(categoriseProductsParams).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000),
        InteractState.Loading
    )

    fun saveBackOnline(status: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStore.saveBackOnline(status)
    }

    init {
        categoriseProductsList.expand {
            _categoryList.value = it
        }
    }
}