package com.sina.local.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sina.model.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemLocal(productEntity: ProductEntity)

    @Query("select * from product_entity")
    fun getItemsLocal(): Flow<List<ProductEntity>>
}