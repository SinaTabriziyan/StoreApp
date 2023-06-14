package com.sina.model.data.customer_dto

import com.google.gson.annotations.SerializedName

data class CustomerDTO(
    val id: Int,
    val email: String,
    val username: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("avatar_url")
    val avatar_url: String,
    val role: String
)