package com.sina.data_item.di

import com.sina.data_item.remote.ItemRemoteDataSource
import com.sina.data_item.remote.ItemRemoteDataSourceImpl
import com.sina.data_item.repository.ItemRepositoryImpl
import com.sina.domain_main.repository.ItemRepository
import com.sina.network.annotation.IODispatcher
import com.sina.network.services.products.ProductsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object ItemDataModule {
    @Provides
    fun provideItemDataSource(productsService: ProductsService): ItemRemoteDataSource = ItemRemoteDataSourceImpl(productsService)

    @Provides
    fun provideItemRepository(
        itemRemoteDataSource: ItemRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ItemRepository = ItemRepositoryImpl(itemRemoteDataSource, ioDispatcher)

}