package com.sina.local.data.di

import android.app.Application
import androidx.room.Room
import com.sina.local.data.annotation.DatabaseName
import com.sina.local.data.db.ProductDao
import com.sina.local.data.db.ProductDatabase
import com.sina.local.params.LocalParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    @DatabaseName
    fun provideDatabaseName(): String = LocalParams.DATABASE_NAME

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
        @DatabaseName dataBase: String,
    ): ProductDatabase =
        Room.databaseBuilder(application, ProductDatabase::class.java, dataBase)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideStoreDao(appDatabase: ProductDatabase): ProductDao = appDatabase.getStoreDao()
}