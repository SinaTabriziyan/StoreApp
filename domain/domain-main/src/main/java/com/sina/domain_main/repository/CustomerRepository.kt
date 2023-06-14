package com.sina.domain_main.repository

import com.sina.model.ui.customer_item.Customer

interface CustomerRepository {
    suspend fun createCustomer(customer: Customer): Customer
}