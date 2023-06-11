package com.sina.network.annotation

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ConsumerKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ConsumerSecret