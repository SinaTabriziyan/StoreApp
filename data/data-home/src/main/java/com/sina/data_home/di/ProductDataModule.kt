package com.sina.data_home.di

import com.sina.data_home.remote.ProductsRemoteDataSource
import com.sina.data_home.remote.ProductsRemoteDataSourceImpl
import com.sina.data_home.repository.ProductsRepositoryImpl
import com.sina.domain_main.repository.ProductsRepository
import com.sina.network.annotation.IODispatcher
import com.sina.network.services.products.ProductsService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductDataModule {

    @Provides
    fun provideProductDataSource(productsService: ProductsService): ProductsRemoteDataSource = ProductsRemoteDataSourceImpl(productsService)

    @Provides
    fun provideProductRepository(
        productsRemoteDataSource: ProductsRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ProductsRepository = ProductsRepositoryImpl(productsRemoteDataSource, ioDispatcher)

}