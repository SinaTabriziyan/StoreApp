package com.sina.network.di

import com.sina.common.anotations.CpuDispatcher
import com.sina.common.anotations.DefaultDispatcher
import com.sina.common.anotations.IODispatcher
import com.sina.common.anotations.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @IODispatcher
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @CpuDispatcher
    fun providesCpuDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}
