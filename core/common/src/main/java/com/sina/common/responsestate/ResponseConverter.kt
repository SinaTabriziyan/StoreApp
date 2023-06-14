package com.sina.common.responsestate

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import javax.inject.Inject

class ResponseConverter @Inject constructor(
    private val gson: Gson
) {
    @Throws()
    operator fun <T> invoke(
        response: Response<T>
    ): T {
        return if (response.isSuccessful) {
            val body = response.body()
            when {
                response.code() in 200..201 && body != null -> body
                body == null -> throw NoBodyException()
                else -> throw Exception()
            }
        } else {
            val errorBody = response.errorBody()
            val networkError = gson.fromJson(errorBody?.charStream(), NetworkError::class.java)
//            Timber.d("Network request failed with error: $networkError")

            throw NetworkException(error = networkError.asError())
        }
    }

}

data class NetworkError(
    @SerializedName("code")
    val identity: String,
    @SerializedName("status_message")
    val message: String,
    val networkStatus: NetworkStatus
)

data class NetworkStatus(
    @SerializedName("status")
    val code: Int
)
class NoBodyException(msg: String? = null) : Exception(msg)

class NetworkException(val error: Error, msg: String? = null) : Exception(msg)

data class Error(
    val identity: String,
    val message: String,
    val status: Status
)

data class Status(
    val code: Int
)

fun NetworkError.asError() = Error(
    identity = identity,
    message = message,
    status = networkStatus.asStatus()
)

fun NetworkStatus.asStatus() = Status(code)