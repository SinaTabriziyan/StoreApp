package com.sina.common.di.qualifire

import com.sina.common.enums.OwnDispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val niaDispatcher: OwnDispatchers)
