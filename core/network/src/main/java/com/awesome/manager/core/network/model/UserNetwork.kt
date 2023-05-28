package com.awesome.manager.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNetwork(
    val id:String,
    val email:String,
    @SerialName("email_confirmed_at") val emailConfirmedAt:String,
    @SerialName("created_at") val createdAt:String,
    @SerialName("updated_at") val updatedAt:String,
)