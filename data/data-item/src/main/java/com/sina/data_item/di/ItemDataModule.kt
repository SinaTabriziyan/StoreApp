package com.sina.data_item.di

import com.sina.data_item.local.ItemLocalDataSource
import com.sina.data_item.local.ItemLocalDataSourceImpl
import com.sina.data_item.remote.ItemRemoteDataSource
import com.sina.data_item.remote.ItemRemoteDataSourceImpl
import com.sina.data_item.repository.ItemRepositoryImpl
import com.sina.domain_main.repository.ItemRepository
import com.sina.local.data.db.ProductDao
import com.sina.common.anotations.IODispatcher
import com.sina.network.services.StoreServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ItemDataModule {
    @Provides
    fun provideItemRemoteDataSource(storeServices: StoreServices): ItemRemoteDataSource = ItemRemoteDataSourceImpl(storeServices)

    @Provides
    @Singleton
    fun provideItemLocalDataSource(productDao: ProductDao): ItemLocalDataSource = ItemLocalDataSourceImpl(productDao)

    @Provides
    fun provideItemRepository(
        itemRemoteDataSource: ItemRemoteDataSource,
        itemLocalDataSource: ItemLocalDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ItemRepository = ItemRepositoryImpl(itemRemoteDataSource, itemLocalDataSource, ioDispatcher)

}