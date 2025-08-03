package com.awesome.manager.core.network.model.request

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Resource("rest/v1/accounts")
data object Account {

    @Resource("")
    class Get(
        @SerialName("updated_at") val updatedAt: String,
        @SerialName("parent") val parent: Account = Account,
        @SerialName("select") val select: String = "*",
    )

    @Resource("")
    class Insert(
        @SerialName("parent") val parent: Account = Account
    )

    @Resource("")
    class Update(
        @SerialName("id") val accountID: String,
        @SerialName("parent") val parent: Account = Account,
    )

}

@Serializable
data class AccountNetworkRequest(
    @SerialName("id") val id: String,
    @SerialName("creator_user_id") val creatorUserId: String,
    @SerialName("name") val name: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("currency_code") val currencyCode: String,
    @SerialName("default_transaction_type") val defaultTransactionType: String,
)