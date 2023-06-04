package com.awesome.manager.core.network.model

import kotlinx.serialization.SerialName

data class AccountNetwork(
    @SerialName("id") val id:String,
    @SerialName("name") val name:String,
    @SerialName("image_url") val imageUrl:String,
    @SerialName("default_currency_id") val defaultCurrencyId:String,
    @SerialName("default_transactions_type") val defaultTransactionsType:String,
    @SerialName("creator_user_id") val creatorUserId:String,
    @SerialName("created_at") val createdAt:String,
    @SerialName("updated_at") val updatedAt:String,
)