package com.sina.data_category.di

import com.sina.data_category.remote.CategoryRemoteDataSource
import com.sina.data_category.remote.CategoryRemoteDataSourceImpl
import com.sina.network.services.products.ProductsService
import dagger.Provides

object CategoryModule {


    @Provides
    fun provideProductDataSource(productsService: ProductsService): CategoryRemoteDataSource = CategoryRemoteDataSourceImpl(productsService)
}