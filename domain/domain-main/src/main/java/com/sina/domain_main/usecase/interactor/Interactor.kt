package com.sina.domain_main.usecase.interactor

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

abstract class Interactor<P, R> {

    operator fun invoke(
        params: P, timeout: Duration = DefaultTimeout,
    ): Flow<InteractState<R>> = doWork(params).map { r ->
        withTimeout(timeout) {
            InteractState.Success(r) as InteractState<R>
        }
    }.catch { e ->
        Log.e("TAG", "invoke: Error ${e.message.toString()}", )
        emit(InteractState.Error(e.message.toString())) }.onStart {
        Log.e("TAG", "invoke: Start", )
        emit(InteractState.Loading)
    }

    protected abstract fun doWork(params: P): Flow<R>

    companion object {
        private val DefaultTimeout = 100.milliseconds
    }
}
