package com.sina.model.mapper

import com.sina.model.network.customer_dto.CustomerDTO
import com.sina.model.ui.customer_item.Customer

fun mapCustomerDtoToCustomer(dto: CustomerDTO): Customer = with(dto) {
    Customer(id, email, username, firstName, lastName, avatar_url, role)
}

fun mapCustomerToCustomerDto(customer: Customer): CustomerDTO = with(customer) {
    CustomerDTO(id, email, username, firstName, lastName, avatarUrl, role)
}