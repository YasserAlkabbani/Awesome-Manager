package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import com.awesome.manager.core.network.model.request.AccountNetworkRequest
import com.awesome.manager.core.network.model.response.AccountNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.patch
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import javax.inject.Inject


class KtorAccountNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AccountNetworkDataSource {

    override suspend fun returnUpdatedAccounts(updatedAt: String): List<AccountNetworkResponse> =
        httpClient.get(Account.Get(updatedAt = "gt.$updatedAt")).body()

    override suspend fun upsertAccount(accountNetworkRequest: AccountNetworkRequest): Unit =
        httpClient.post(Account.Upsert) {
            header(HttpHeaders.Prefer, "resolution=merge-duplicates")
            setBody(accountNetworkRequest)
        }.body()

}

@Resource("rest/v1/accounts")
private data object Account {

    @Resource("")
    class Get(
        @SerialName("updated_at") val updatedAt: String,
        @SerialName("parent") val parent: Account = Account,
        @SerialName("select") val select: String = "*",
    )

    @Resource("")
    data class Upsert(
        @SerialName("parent") val parent: Account = Account
    )

}