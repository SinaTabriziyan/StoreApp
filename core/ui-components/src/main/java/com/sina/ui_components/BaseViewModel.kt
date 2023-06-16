package com.sina.ui_components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    protected val _uiState = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> get() = _uiState

    enum class UiState {
        Success, Loading
    }
}