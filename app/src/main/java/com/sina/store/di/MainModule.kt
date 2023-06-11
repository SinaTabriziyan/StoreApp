package com.sina.store.di

import com.sina.common.navigator.Navigator
import com.sina.store.navigation.DefaultNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MainModule {
    @Provides
    @Singleton
    fun provideNavigatorProviders(): Navigator.Provider = DefaultNavigator()
}