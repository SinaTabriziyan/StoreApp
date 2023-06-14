package com.sina.domain_main.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

abstract class UseCaseFlow<PARAMS, RESPONSE> {
    operator fun invoke(
        params: PARAMS, timeout: Duration = DefaultTimeout,
    ): Flow<InteractState<RESPONSE>> = execute(params).map { r ->
        withTimeout(timeout) { InteractState.Success(r) as InteractState<RESPONSE> }
    }.catch { e ->
        emit(InteractState.Error(e.message.toString()))
    }.onStart {
        emit(InteractState.Loading)
    }

    protected abstract fun execute(params: PARAMS): Flow<RESPONSE>

    companion object {
        private val DefaultTimeout = 100.milliseconds
    }
}

abstract class UseCase<PARAMS, RESPONSE> {
    suspend operator fun invoke(params: PARAMS): InteractState<RESPONSE> {
        return try {
            execute(params).let { InteractState.Success(it) }
        } catch (e: Exception) {
            InteractState.Error(e.message.toString())
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(params: PARAMS): RESPONSE
}
