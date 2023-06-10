package com.sina.domain_main.usecase.interactor


sealed class InteractState<out T> {
    data class Success<T>(val data: T) : InteractState<T>()
    object Loading : InteractState<Nothing>()
    object Error : InteractState<Nothing>()
}