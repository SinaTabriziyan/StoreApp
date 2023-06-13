package com.sina.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    val userName: String,
    @PrimaryKey(autoGenerate = false)
    val productId: Int,
    val productNumber: Int,
)