package com.awesome.manager.core.network.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CurrencyNetwork(
    @SerialName("id") val id:String,
    @SerialName("country_name") val countryName:String,
    @SerialName("image_url") val imageUrl:String,
    @SerialName("currency_code") val currencyCode:String,
    @SerialName("currency_name") val currencyName:String,
    @SerialName("currency_symbol") val currencySymbol:String,
    @SerialName("created_at") val createdAt:String,
    @SerialName("updated_at") val updatedAt:String,
)