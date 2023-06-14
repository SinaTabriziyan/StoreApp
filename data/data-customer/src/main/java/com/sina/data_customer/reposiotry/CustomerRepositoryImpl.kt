package com.sina.data_customer.reposiotry

import com.sina.data_customer.remote.CustomerRemoteDataSource
import com.sina.domain_main.repository.CustomerRepository
import com.sina.model.mapper.mapCustomerToCustomerDto
import com.sina.model.ui.customer_item.Customer

class CustomerRepositoryImpl(
    private val customerRemoteDataSource: CustomerRemoteDataSource,
) : CustomerRepository {
    override suspend fun createCustomer(customer: Customer): Customer =
        customerRemoteDataSource.createCustomer(mapCustomerToCustomerDto(customer))
}