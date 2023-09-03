package com.awesome.manager.core.network

import com.awesome.manager.core.common.AmError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
                null->AmError.OtherError(null)
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