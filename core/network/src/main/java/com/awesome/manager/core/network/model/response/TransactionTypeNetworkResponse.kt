package com.awesome.manager.core.network.model.response

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionTypeNetworkResponse(
    @SerialName("id") val id: String,
    @SerialName("type") val type: String,
    @SerialName("is_positive") val isPositive: Boolean,
    @SerialName("is_close") val isClose: Boolean,
    @SerialName("created_at") val createdAt: String
)