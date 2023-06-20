package com.sina.feature_search.ui.order

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.sina.common.constants.Constants
import com.sina.feature_search.ui.adapters.model.SearchFilterItem
import com.sina.feature_search.ui.adapters.model.SearchOrderItem
import com.sina.local.data.datastore.AppDataStore
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchOrderByViewModel @Inject constructor(    private val dataStore: AppDataStore
) : BaseViewModel() {
    fun saveSearchOrderBy(orderTitle: SearchOrderItem) = viewModelScope.launch(Dispatchers.IO) {
        dataStore.saveSearchOrderByType(orderTitle.orderTitle, orderTitle.id)
    }


//    var searchOrderType = Constants.DEFAULT_SEARCH_TYPE

    val filterList = listOf<SearchOrderItem>(
        SearchOrderItem(1, "date"),
        SearchOrderItem(2, "rating"),
        SearchOrderItem(3, "popularity"),
    )


}
