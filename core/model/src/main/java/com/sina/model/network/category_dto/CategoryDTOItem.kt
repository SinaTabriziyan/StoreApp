package com.sina.model.network.category_dto


import com.google.gson.annotations.SerializedName

data class CategoryDTOItem(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("display")
    val display: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("_links")
    val links: Links?,
    @SerializedName("menu_order")
    val menuOrder: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("parent")
    val parent: Int?,
    @SerializedName("slug")
    val slug: String?,
)

data class CategoryItem(
    val description: String?,
    val id: Int?,
    val image: Image?,
    val name: String?,
)