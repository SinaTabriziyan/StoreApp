package com.sina.ui_components

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.interactor.InteractState
import com.sina.local.data.datastore.AppDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel  constructor(val dataStore: AppDataStore) : ViewModel() {


    val uiState = MutableStateFlow(UiState.Loading)

    protected val _netWorkState = MutableStateFlow(false)
    val netWorkState: StateFlow<Boolean> get() = _netWorkState
    var networkStatus = false
    var backOnline = false

    enum class UiState { Success, Loading, Error }

    fun showNetworkStatue(context: Context) {
        if (!networkStatus) {
            Toast.makeText(context, "دسترسی به اینترنت را چک کنید", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)

        } else if (networkStatus) {
            if (backOnline)
                Toast.makeText(context, "اتصال به شبکه انجام شد", Toast.LENGTH_SHORT).show()
            saveBackOnline(false)
        }
    }

    fun saveBackOnline(backOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveBackOnline(backOnline)
        }
    }
}