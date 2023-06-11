package com.sina.domain_main.di

import com.sina.domain_main.repository.ProductsRepository
import com.sina.domain_main.usecase.ProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetProducts(productsRepository: ProductsRepository): ProductsUseCase = ProductsUseCase(productsRepository)
}