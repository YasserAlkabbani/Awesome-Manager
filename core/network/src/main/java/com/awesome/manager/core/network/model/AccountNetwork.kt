package com.awesome.manager.core.network.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AccountNetworkResponse(
    @SerialName("id") val id:String,
    @SerialName("creator_user_id") val creatorUserId:String,
    @SerialName("currency_id") val currencyId:String,
    @SerialName("default_transaction_type_id") val defaultTransactionTypeId:String,
    @SerialName("name") val name:String,
    @SerialName("image_url") val imageUrl:String,
    @SerialName("created_at") val createdAt:String?,
    @SerialName("updated_at") val updatedAt:String?,
)
@Serializable
data class AccountNetworkRequest(
    @SerialName("id") val id:String,
    @SerialName("creator_user_id") val creatorUserId:String,
    @SerialName("currency_id") val currencyId:String,
    @SerialName("default_transaction_type_id") val defaultTransactionTypeId:String,
    @SerialName("name") val name:String,
    @SerialName("image_url") val imageUrl:String
)