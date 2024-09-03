package com.awesome.manager.core.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class NetworkError : Throwable() {
    data object ConnectionError:NetworkError()
    data object Timeout:NetworkError()
    data object BadRequest:NetworkError()
    data object ConvertData:NetworkError()
    data object InternalServerError:NetworkError()
    data object RedirectResponseException:NetworkError()
    data object Unauthorized:NetworkError()
    data object Forbidden:NetworkError()
    data object RequestTimeout:NetworkError()
    data object UnknownError:NetworkError()
    data object TooManyRequests:NetworkError()
    data class OtherError(val errorMessage:String):NetworkError()
}

@Serializable
data class ErrorResponse(
    @SerialName("error") val error: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("error_description") val errorDescription: String? = null,
    @SerialName("msg") val msg: String? = null
){
    fun getErrorMessage():String=message?:msg?:errorDescription?:error?:"Unknown Error"
}