package com.awesome.manager.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionNetworkResponse(
    @SerialName("id") val id: String,
    @SerialName("creator_user_id") val creatorUserId: String,
    @SerialName("account_id") val accountId: String,
    @SerialName("transaction_type") val transactionType: String,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("amount") val amount: Double,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("transaction_at") val transactionAt: String
)

@Serializable
data class TransactionNetworkRequest(
    @SerialName("id") val id: String,
    @SerialName("creator_user_id") val creatorUserId: String,
    @SerialName("account_id") val accountId: String,
    @SerialName("transaction_type") val transactionType: String,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("amount") val amount: Double,
    @SerialName("transaction_at") val transactionAt: String,
)