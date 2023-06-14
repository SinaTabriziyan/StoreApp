package com.sina.model.mapper

import com.sina.model.data.customer_dto.CustomerDTO
import com.sina.model.ui.customer_item.Customer

fun CustomerDTO.asCustomer() = Customer(
    id = id,
    email = email,
    username = username,
    firstName = firstName,
    lastName = lastName,
    avatarUrl = avatar_url,
    role = role,
)

fun Customer.asCustomerNetwork() = CustomerDTO(
    id = id,
    email = email,
    username = username,
    firstName = firstName,
    lastName = lastName,
    avatar_url = avatarUrl,
    role = role
)