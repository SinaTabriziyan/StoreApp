package com.sina.feature_customer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain_main.usecase.CreateCustomerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val createCustomerUseCase: CreateCustomerUseCase) : ViewModel() {
    fun createUser(userName: String, firstName: String, lastName: String, email: String, avatar: String) {
        viewModelScope.launch {
            createCustomerUseCase(CreateCustomerUseCase.Params(1, email, userName, firstName, lastName, avatar, ""))
        }
    }
}