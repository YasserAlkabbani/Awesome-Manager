package com.awesome.manager.core.network.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccountNetworkResponse(
    @SerialName("id") val id: String,
    @SerialName("creator_user_id") val creatorUserID: String,
    @SerialName("currency_id") val currencyID: String,
    @SerialName("default_transaction_type_id") val defaultTransactionTypeID: String,
    @SerialName("name") val name: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("created_at") val createdAt: String?,
    @SerialName("updated_at") val updatedAt: String?,
)