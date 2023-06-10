package com.sina.store.di

import com.sina.common.constatnts.local.LocalParams.DATABASE_NAME
import com.sina.local.data.annotation.DatabaseName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    @DatabaseName
    fun provideDatabaseName(): String = DATABASE_NAME
//
//    @Provides
//    @Singleton
//    fun provideDatabase(
//        application: Application,
//        @DatabaseName dataBase: String,
//    ): AppDatabase =
//        Room.databaseBuilder(application, AppDatabase::class.java, dataBase)
//            .fallbackToDestructiveMigration()
//            .build()
//
//    @Provides
//    @Singleton
//    fun provideStoreDao(appDatabase: AppDatabase): StoreDao = appDatabase.getStoreDao()


}