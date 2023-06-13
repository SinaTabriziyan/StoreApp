package com.sina.data_search.di

import com.sina.data_search.remote.SearchProductsRemoteDataSource
import com.sina.data_search.remote.SearchProductsRemoteDataSourceImpl
import com.sina.data_search.repository.SearchProductsRepositoryImpl
import com.sina.domain_main.repository.SearchProductsRepository
import com.sina.network.annotation.IODispatcher
import com.sina.network.services.products.ProductsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object SearchProductsDataModule {

    @Provides
    fun provideSearchProductsRemoteDataSource(productsService: ProductsService): SearchProductsRemoteDataSource =
        SearchProductsRemoteDataSourceImpl(productsService)

    @Provides
    fun provideSearchProductsRepository(
        searchProductsRemoteDataSource: SearchProductsRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): SearchProductsRepository = SearchProductsRepositoryImpl(searchProductsRemoteDataSource, ioDispatcher)
}