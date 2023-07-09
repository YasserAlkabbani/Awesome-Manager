package com.awesome.manager.core.network.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class TransactionNetwork(
    @SerialName("id") val id:String,
    @SerialName("creator_user_id") val creatorUserId:String,
    @SerialName("account_id") val accountId:String,
    @SerialName("transaction_type_id") val transactionTypeId:String,
    @SerialName("title") val title:String,
    @SerialName("subtitle") val subtitle:String,
    @SerialName("amount") val amount:Double,
    @SerialName("is_pay") val isPay:Boolean,
    @SerialName("created_at") val createdAt:String,
    @SerialName("updated_at") val updatedAt:String
)