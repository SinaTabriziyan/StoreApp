package com.sina.model.network.products_dto


import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("slug")
    val slug: String?
)