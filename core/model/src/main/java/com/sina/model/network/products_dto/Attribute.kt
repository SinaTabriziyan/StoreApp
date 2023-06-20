package com.sina.model.network.products_dto


import com.google.gson.annotations.SerializedName

data class Attribute(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("options")
    val options: List<String?>?,
    @SerializedName("position")
    val position: Int?,
    @SerializedName("variation")
    val variation: Boolean?,
    @SerializedName("visible")
    val visible: Boolean?
)