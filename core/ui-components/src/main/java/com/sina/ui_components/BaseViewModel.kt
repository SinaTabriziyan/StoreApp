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

abstract class BaseViewModel : ViewModel() {
    protected val _netWorkState = MutableStateFlow(false)
    val uiState = MutableStateFlow(UiState.Loading)
    var networkStatus = false
    var backOnline = false
    val netWorkState: StateFlow<Boolean> get() = _netWorkState

    enum class UiState { Success, Loading, Error }

    inline fun <reified T> StateFlow<InteractState<T>>.expand(crossinline action: suspend (T) -> Unit) {
        viewModelScope.launch {
            collectLatest {
                when (it) {
                    is InteractState.Error -> {
//                        Timber.e("Error ${it.errorMessage}")
                        uiState.value = UiState.Error
                    }

                    is InteractState.Loading -> uiState.value = UiState.Loading
                    is InteractState.Success -> {
                        uiState.value = UiState.Success
                        action(it.data)
                    }
                }
            }
        }
    }
}