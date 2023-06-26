package com.awesome.manager.core.network.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class TransactionTypesNetwork(
    @SerialName("id") val id:String,
    @SerialName("title") val title:String,
    @SerialName("is_close") val isClose:Boolean,
    @SerialName("created_at") val createdAt:String,
    @SerialName("updated_at") val updatedAt:String
)