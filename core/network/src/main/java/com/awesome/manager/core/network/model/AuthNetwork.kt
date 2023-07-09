package com.awesome.manager.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthNetwork(
    @SerialName ("access_token") val accessToken:String,
    @SerialName ("refresh_token") val refreshToken:String,
    @SerialName ("user") val authUserNetwork:AuthUserNetwork,
)

@Serializable
data class AuthUserNetwork(
    @SerialName("id") val id:String,
    @SerialName("email") val email:String,
    @SerialName("email_confirmed_at") val emailConfirmedAt:String,
    @SerialName("created_at") val createdAt:String,
    @SerialName("updated_at") val updatedAt:String,
)