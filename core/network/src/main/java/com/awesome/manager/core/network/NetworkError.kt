package com.awesome.manager.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class NetworkError:Throwable() {
    class ConnectionError : NetworkError()
    class ConvertDataError : NetworkError()
    class InternalServerError : NetworkError()
    class Unauthorized : NetworkError()
    class Forbidden : NetworkError()
    class RequestTimeout : NetworkError()
    class TooManyRequests : NetworkError()
    data class BadRequest(val errorMessage: String) : NetworkError()
    data class OtherError(val errorMessage: String) : NetworkError()
}

@Serializable
data class ErrorResponse(
    @SerialName("error") val error: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("error_description") val errorDescription: String? = null,
    @SerialName("msg") val msg: String? = null
) {
    fun getErrorMessage(): String = message ?: msg ?: errorDescription ?: error ?: "Unknown Error"
}