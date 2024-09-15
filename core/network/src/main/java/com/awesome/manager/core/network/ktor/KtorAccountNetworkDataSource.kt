package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import com.awesome.manager.core.network.model.request.Account
import com.awesome.manager.core.network.model.request.AccountNetworkRequest
import com.awesome.manager.core.network.model.response.AccountNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import javax.inject.Inject

class KtorAccountNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AccountNetworkDataSource {

    override suspend fun returnUpdatedAccount(updatedAt: String): List<AccountNetworkResponse> =
        httpClient.get(Account.Get(updatedAt = "gt.$updatedAt")).body()


    override suspend fun upsertAccount(accountNetwork: AccountNetworkRequest): Unit =
        httpClient.post(Account.Upsert()) {
            header("Prefer", "resolution=merge-duplicates")
            setBody(accountNetwork)
        }.body()

}