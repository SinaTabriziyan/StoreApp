package com.sina.domain_main.usecase.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

abstract class Interactor<P, R> {
    suspend operator fun invoke(
        params: P, timeout: Duration = DefaultTimeout,
    ): Flow<InteractState<R>> = doWork(params).map { r ->
        withTimeout(timeout) {
            InteractState.Success(r) as InteractState<R>
        }
    }.catch { emit(InteractState.Error) }.onStart { emit(InteractState.Loading) }

    protected abstract suspend fun doWork(params: P): Flow<R>

    companion object {
        private val DefaultTimeout = 5.milliseconds
    }
}
