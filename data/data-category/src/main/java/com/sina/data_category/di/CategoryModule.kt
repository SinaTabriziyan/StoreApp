package com.sina.data_category.di

import com.sina.data_category.remote.CategoryRemoteDataSource
import com.sina.data_category.remote.CategoryRemoteDataSourceImpl
import com.sina.data_category.repository.CategoryRepositoryImpl
import com.sina.domain_main.repository.CategoryRepository
import com.sina.network.annotation.IODispatcher
import com.sina.network.services.StoreServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryModule {

    @Provides
    @Singleton
    fun provideProductDataSource(storeServices: StoreServices): CategoryRemoteDataSource =
        CategoryRemoteDataSourceImpl(storeServices)

    @Provides
    @Singleton
    fun provideCategoryRepository(
        productDataSource: CategoryRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): CategoryRepository = CategoryRepositoryImpl(productDataSource, ioDispatcher)

}