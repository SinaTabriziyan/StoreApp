package com.sina.common.responsestate

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.concurrent.TimeUnit


sealed class ResponseState<out T> {
    data class Success<T>(val data: T) : ResponseState<T>()
    data class Error(val exception: Throwable? = null) : ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Error -> "Failure ${exception?.message}"
            is Success -> "Success $data"
            is Loading -> "loading"
        }
    }
}

fun <T> Flow<T>.asResult(): Flow<ResponseState<T>> = map<T, ResponseState<T>> { ResponseState.Success(it) }
    .onStart { emit(ResponseState.Loading) }
    .catch { emit(ResponseState.Error(it)) }


//fun <T> Flow<ResponseState<T>>.create() =map { it.pure() }
//fun <T> ResponseState<T>.pure() :T= when(this){
//    is ResponseState.Error -> TODO()
//is    ResponseState.Loading -> TODO()
//    is ResponseState.Success -> TODO()
//}

//abstract class InteracResult<P, Q> {
//    operator fun invoke(params: P, timeOutMs: Long = diffaultTimeOut):Flow<>
//
//    companion object {
//        private val diffaultTimeOut = TimeUnit.MINUTES.toMillis(5)
//    }
//
//    protected abstract fun doWork(params: P): Flow<Q>
//}