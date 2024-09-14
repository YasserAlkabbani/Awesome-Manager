package com.awesome.manager.core.network.model.request

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Resource("rest/v1/accounts")
data object Account {

    @Resource("")
    class Get(
        @SerialName("parent") val parent: Account = Account,
        @SerialName("updated_at") val updatedAt: String,
        @SerialName("select") val select: String = "*"
    )

    @Resource("")
    class Upsert(
        @SerialName("parent") val parent: Account = Account,
    )

}

@Serializable
data class AccountNetworkRequest(
    @SerialName("id") val id: String,
    @SerialName("creator_user_id") val creatorUserId: String,
    @SerialName("currency_id") val currencyId: String,
    @SerialName("default_transaction_type") val defaultTransactionType: String,
    @SerialName("name") val name: String,
    @SerialName("image_url") val imageUrl: String
)