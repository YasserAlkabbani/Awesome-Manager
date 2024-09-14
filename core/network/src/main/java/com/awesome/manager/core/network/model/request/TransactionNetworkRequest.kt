package com.awesome.manager.core.network.model.request

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Resource("rest/v1/transactions")
data object Transaction {
    @Resource("")
    class Get(
        @SerialName("parent") val parent: Transaction = Transaction,
        @SerialName("updated_at") val updatedAt: String,
        @SerialName("select") val select: String = "*"
    )

    @Resource("")
    class Upsert(
        @SerialName("parent") val parent: Transaction = Transaction,
    )

}

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