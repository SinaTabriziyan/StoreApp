package com.sina.data_customer.remote

import com.sina.model.data.customer_dto.CustomerDTO
import com.sina.model.mapper.mapCustomerDtoToCustomer
import com.sina.model.ui.customer_item.Customer
import com.sina.network.services.StoreServices

class CustomerRemoteDataSourceImpl(private val storeServices: StoreServices) : CustomerRemoteDataSource {
    override suspend fun createCustomer(customerDTO: CustomerDTO): Customer =
        mapCustomerDtoToCustomer(storeServices.createCustomer(customerDTO))
}