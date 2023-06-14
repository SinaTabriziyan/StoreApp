package com.sina.data_customer.di

import com.sina.data_customer.remote.CustomerRemoteDataSource
import com.sina.data_customer.remote.CustomerRemoteDataSourceImpl
import com.sina.data_customer.reposiotry.CustomerRepositoryImpl
import com.sina.domain_main.repository.CustomerRepository
import com.sina.network.annotation.IODispatcher
import com.sina.network.services.products.StoreServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CustomerModule {

    @Singleton
    @Provides
    fun provideCustomerDataSource(storeServices: StoreServices): CustomerRemoteDataSource =
        CustomerRemoteDataSourceImpl(storeServices)


    @Provides
    @Singleton
    fun provideCustomerRepository(
        customerRemoteDataSource: CustomerRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): CustomerRepository = CustomerRepositoryImpl(customerRemoteDataSource)

}