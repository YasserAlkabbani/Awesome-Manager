package com.awesome.manager.core.network.model.response

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionNetworkResponse(
    @SerialName("id") val id: String,
    @SerialName("creator_user_id") val creatorUserID: String,
    @SerialName("account_id") val accountID: String,
    @SerialName("transaction_type_id") val transactionTypeID: String,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("amount") val amount: Double,
    @SerialName("transaction_at") val transactionAt: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)