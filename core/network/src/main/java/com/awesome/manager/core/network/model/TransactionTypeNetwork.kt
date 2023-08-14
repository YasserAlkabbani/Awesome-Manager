package com.awesome.manager.core.network.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class TransactionTypesNetwork(
    @SerialName("id") val id:String,
    @SerialName("title") val title:String,
    @SerialName("dead_transaction") val deadTransaction:Boolean,
    @SerialName("default_payment_transaction") val defaultPaymentTransaction:Boolean,
    @SerialName("switch_payment_transaction") val switchPaymentTransaction:Boolean,
    @SerialName("created_at") val createdAt:String,
    @SerialName("updated_at") val updatedAt:String
)