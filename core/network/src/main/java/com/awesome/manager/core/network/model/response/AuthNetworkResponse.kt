package com.awesome.manager.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthNetwork(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("user") val authUserNetwork: AuthUserNetwork,
){
    fun asUserNetwork(): UserNetwork = UserNetwork(
        id = authUserNetwork.id,
        name = "",
        imageUrl = "",
        email = authUserNetwork.email,
        createdAt = authUserNetwork.createdAt,
        updatedAt = authUserNetwork.updatedAt,
        accessToken = accessToken,
        refreshToken=refreshToken
    )
}

@Serializable
data class AuthUserNetwork(
    @SerialName("id") val id: String,
    @SerialName("email") val email: String,
    @SerialName("email_confirmed_at") val emailConfirmedAt: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("identities") val identities: List<Identity>,
) {
    @Serializable
    data class Identity(
        @SerialName("user_id") val userId: String,
        @SerialName("provider") val provider: String,
    )
}