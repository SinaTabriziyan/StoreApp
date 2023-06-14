package com.sina.local.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sina.model.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getStoreDao(): ProductDao
}