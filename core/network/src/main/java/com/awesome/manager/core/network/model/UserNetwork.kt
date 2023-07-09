package com.awesome.manager.core.network.model

import kotlinx.serialization.SerialName

data class UserNetwork(
    @SerialName("id") val id:String,
    @SerialName("name") val name:String,
    @SerialName("image_url") val imageUrl:String,
    @SerialName("email") val email:String,
    @SerialName("created_at") val createdAt:String,
    @SerialName("updated_at") val updatedAt:String,
)