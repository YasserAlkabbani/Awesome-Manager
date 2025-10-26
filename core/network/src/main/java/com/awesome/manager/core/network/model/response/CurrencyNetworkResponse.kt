package com.awesome.manager.core.network.model.response

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyNetwork(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("code") val code: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("created_at") val createdAt: String
)