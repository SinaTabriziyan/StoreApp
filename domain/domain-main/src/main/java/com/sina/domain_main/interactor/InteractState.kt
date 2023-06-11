package com.sina.domain_main.interactor


sealed class InteractState<out T> {
    data class Success<T>(val data: T) : InteractState<T>()
    object Loading : InteractState<Nothing>()
    data class Error(val errorMessage: String) : InteractState<Nothing>()
}