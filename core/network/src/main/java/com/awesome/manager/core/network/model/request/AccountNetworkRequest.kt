package com.awesome.manager.core.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccountNetworkRequest(
    @SerialName("id") val id: String,
    @SerialName("creator_user_id") val creatorUserId: String,
    @SerialName("currency_id") val currencyID: String,
    @SerialName("default_transaction_type_id") val defaultTransactionTypeID: String,
    @SerialName("name") val name: String,
    @SerialName("image_url") val imageUrl: String?,
)