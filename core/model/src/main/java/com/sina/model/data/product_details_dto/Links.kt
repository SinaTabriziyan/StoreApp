package com.sina.model.data.product_details_dto


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("collection")
    val collection: List<Collection?>?,
    @SerializedName("self")
    val self: List<Self?>?
)