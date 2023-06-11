package com.awesome.manager.core.network

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
        HttpStatusCode.BadRequest-> body<ErrorResponse>().let {
            throw Throwable(it.errorDescription?:it.error?:it.message)
        }
        HttpStatusCode.Unauthorized->body<UnauthorizedResponse>().let {
            throw Throwable(it.msg)
        }
        else -> throw Throwable("UNKNOWN ERROR")
    }
}