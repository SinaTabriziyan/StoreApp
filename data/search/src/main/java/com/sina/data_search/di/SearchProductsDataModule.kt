package com.sina.data_search.di

import com.sina.data_search.remote.SearchProductsRemoteDataSource
import com.sina.data_search.remote.SearchProductsRemoteDataSourceImpl
import com.sina.data_search.repository.SearchProductsRepositoryImpl
import com.sina.domain_main.repository.SearchProductsRepository
import com.sina.common.anotations.IODispatcher
import com.sina.network.services.StoreServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object SearchProductsDataModule {

    @Provides
    fun provideSearchProductsRemoteDataSource(storeServices: StoreServices): SearchProductsRemoteDataSource =
        SearchProductsRemoteDataSourceImpl(storeServices)

    @Provides
    fun provideSearchProductsRepository(
        searchProductsRemoteDataSource: SearchProductsRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): SearchProductsRepository = SearchProductsRepositoryImpl(searchProductsRemoteDataSource, ioDispatcher)
}