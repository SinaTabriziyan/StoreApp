package com.sina.network.di

import com.sina.network.params.ServerParams.BASE_URL
import com.sina.network.params.ServerParams.CONSUMER_KEY
import com.sina.network.params.ServerParams.CONSUMER_KEY_VALUE
import com.sina.network.params.ServerParams.CONSUMER_SECRET
import com.sina.network.params.ServerParams.CONSUMER_SECRET_VALUE
import com.sina.network.annotation.BaseUrl
import com.sina.network.annotation.ConsumerKey
import com.sina.network.annotation.ConsumerSecret
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @ConsumerKey
    fun provideConsumerKey(): String = CONSUMER_KEY

    @Provides
    @Singleton
    @ConsumerSecret
    fun provideConsumerSecret(): String = CONSUMER_SECRET

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = BASE_URL


    @Provides
    @Singleton
    fun provideInterceptor(@ConsumerKey consumerKey: String, @ConsumerSecret consumerSecret: String): Interceptor =
        Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter(CONSUMER_KEY_VALUE, consumerKey)
                .addQueryParameter(CONSUMER_SECRET_VALUE, consumerSecret)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }

    @Provides
    @Singleton
    fun provideOkHttpLog(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(interceptor)
        .readTimeout(1, TimeUnit.MINUTES)
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}