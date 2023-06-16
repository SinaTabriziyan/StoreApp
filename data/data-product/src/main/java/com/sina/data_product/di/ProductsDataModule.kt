package com.sina.data_product.di

import com.sina.data_product.remote.ProductsRemoteDataSource
import com.sina.data_product.remote.ProductsRemoteDataSourceImpl
import com.sina.data_product.repository.ProductsRepositoryImpl
import com.sina.domain_main.repository.ProductsRepository
import com.sina.network.annotation.IODispatcher
import com.sina.network.services.StoreServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object ProductsDataModule {

    @Provides
    fun provideProductsDataSource(storeServices: StoreServices): ProductsRemoteDataSource = ProductsRemoteDataSourceImpl(storeServices)

    @Provides
    fun provideProductsRepository(
        productsRemoteDataSource: ProductsRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ProductsRepository = ProductsRepositoryImpl(productsRemoteDataSource, ioDispatcher)

}