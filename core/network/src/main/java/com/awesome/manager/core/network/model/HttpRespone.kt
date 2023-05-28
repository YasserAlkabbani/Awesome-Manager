package com.awesome.manager.core.network.model

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error:String,
    @SerialName("error_description") val errorDescription:String
)

suspend inline fun <reified T> HttpResponse.asResult():T{
    return when(status){
        HttpStatusCode.OK , HttpStatusCode.Created , HttpStatusCode.Accepted->body()
        HttpStatusCode.BadRequest-> body<ErrorResponse>().let {
            throw Throwable(it.errorDescription)
        }
        else -> throw Throwable("UNKNOWN ERROR")
    }
}