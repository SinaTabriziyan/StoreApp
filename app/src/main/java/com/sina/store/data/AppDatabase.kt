package com.sina.store.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sina.model.entity.ItemEntity

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

//    abstract fun getStoreDao(): StoreDao
}