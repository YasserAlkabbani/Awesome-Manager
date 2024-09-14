package com.awesome.manager.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class NetworkError : Throwable() {
    data object ConnectionError : NetworkError()
    data object ConvertDataError : NetworkError()
    data object InternalServerError : NetworkError()
    data object Unauthorized : NetworkError()
    data object Forbidden : NetworkError()
    data object RequestTimeout : NetworkError()
    data object TooManyRequests : NetworkError()
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