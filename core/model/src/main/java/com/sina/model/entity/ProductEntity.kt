package com.sina.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_entity")
data class ProductEntity(
    val userName: String,
    @PrimaryKey(autoGenerate = false)
    val productId: Int,
    val productNumber: Int,
)