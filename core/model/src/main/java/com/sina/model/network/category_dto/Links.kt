package com.sina.model.network.category_dto


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("collection")
    val collection: List<Collection?>?,
    @SerializedName("self")
    val self: List<Self?>?,
    @SerializedName("up")
    val up: List<Up?>?
)