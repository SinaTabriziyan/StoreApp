package com.sina.ui_components

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.sina.domain_main.interactor.InteractState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


abstract class BaseViewModel : ViewModel() {

    val uiState = MutableStateFlow(UiState.Loading)

    protected val _netWorkState = MutableStateFlow(false)
    val netWorkState: StateFlow<Boolean> get() = _netWorkState
    var networkStatus = false
    var backOnline = false

    enum class UiState { Success, Loading, Error }

    abstract fun showNetworkStatue(context: Context)
    abstract fun saveBackOnline(backOnline: Boolean)
}