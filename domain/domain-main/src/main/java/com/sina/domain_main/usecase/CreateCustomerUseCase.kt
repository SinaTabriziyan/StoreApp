package com.sina.domain_main.usecase

import com.sina.domain_main.interactor.UseCase
import com.sina.domain_main.repository.CustomerRepository
import com.sina.model.ui.customer_item.Customer


class CreateCustomerUseCase(private val customerRepository: CustomerRepository) : UseCase<CreateCustomerUseCase.Params, Customer>() {
    data class Params(
        val id: Int,
        val email: String,
        val userName: String,
        val firstName: String,
        val lastName: String,
        val avatar: String,
        val role: String,
    )

    override suspend fun execute(params: Params): Customer =
        with(params) { customerRepository.createCustomer(Customer(1, email, userName, firstName, lastName, avatar, role)) }

}