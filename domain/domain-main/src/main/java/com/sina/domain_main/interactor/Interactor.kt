package com.sina.domain_main.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

abstract class Interactor<PARAMS, RESPONSE> {
    operator fun invoke(
        params: PARAMS, timeout: Duration = DefaultTimeout,
    ): Flow<InteractState<RESPONSE>> = doWork(params).map { r ->
        withTimeout(timeout) { InteractState.Success(r) as InteractState<RESPONSE> }
    }.catch { e ->
        emit(InteractState.Error(e.message.toString())) }.onStart {
        emit(InteractState.Loading)
    }
    protected abstract fun doWork(params: PARAMS): Flow<RESPONSE>

    companion object {
        private val DefaultTimeout = 100.milliseconds
    }
}
