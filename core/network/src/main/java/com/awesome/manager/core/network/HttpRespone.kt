package com.awesome.manager.core.network

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class AmError : Throwable() {
    data object Unauthorized : AmError()
    data object ConnectionError : AmError()
    data object UnknownError : AmError()
    data class BadRequest(val errorMessage: String) : AmError()
    data class OtherError(val errorMessage: String?) : AmError()
}

@Serializable
data class ErrorResponse(
    val error:String?=null,
    val message:String?=null,
    @SerialName("error_description") val errorDescription:String?=null,
)

@Serializable
data class UnauthorizedResponse(
    @SerialName("msg") val msg:String?=null
)

suspend inline fun <reified T> HttpResponse.asResult():T{
    return when(status){
        HttpStatusCode.OK , HttpStatusCode.Created , HttpStatusCode.Accepted->body()
        HttpStatusCode.BadRequest,-> body<ErrorResponse>().let {
            val errorMessage=it.errorDescription?:it.error?:it.message
            throw when(errorMessage){
                null-> AmError.UnknownError
                else -> AmError.BadRequest(errorMessage = errorMessage)
            }
        }
        HttpStatusCode.Unauthorized->body<UnauthorizedResponse>().let {
            throw AmError.Unauthorized
        }
        HttpStatusCode.UnprocessableEntity->body<UnauthorizedResponse>().let {
            throw AmError.OtherError(errorMessage = it.msg)
        }
        HttpStatusCode.TooManyRequests->body<UnauthorizedResponse>().let {
            throw AmError.OtherError(errorMessage = it.msg)
        }
        else -> throw AmError.OtherError(null)
    }
}