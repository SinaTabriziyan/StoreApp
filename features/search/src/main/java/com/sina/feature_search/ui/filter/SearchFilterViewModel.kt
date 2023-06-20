package com.sina.feature_search.ui.filter

import com.sina.local.data.datastore.AppDataStore
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val dataStore: AppDataStore
) : BaseViewModel() {

}