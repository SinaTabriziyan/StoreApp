package com.sina.data_customer.remote

import com.sina.model.network.customer_dto.CustomerDTO
import com.sina.model.ui.customer_item.Customer

interface CustomerRemoteDataSource {
    suspend fun createCustomer(customerDTO: CustomerDTO): Customer
}