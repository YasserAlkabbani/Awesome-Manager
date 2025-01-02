package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import com.awesome.manager.core.network.model.request.Account
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
import javax.inject.Inject

class KtorAccountNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AccountNetworkDataSource {

    override suspend fun getUpdatedAccount(updatedAt: String): List<AccountNetworkResponse> =
        httpClient.get(Account.Get(updatedAt = "gt.$updatedAt")).body()

    override suspend fun insertAccount(accountNetworkRequest: AccountNetworkRequest): List<AccountNetworkResponse> =
        httpClient.post(Account.Insert()) {
            header(HttpHeaders.Prefer, "return=representation")
            setBody(accountNetworkRequest)
        }.body()

    override suspend fun updateAccount(accountNetworkRequest: AccountNetworkRequest): List<AccountNetworkResponse> =
        httpClient.patch(Account.Update(accountID = "eq.${accountNetworkRequest.id}")) {
            header(HttpHeaders.Prefer, "return=representation")
            setBody(accountNetworkRequest)
        }.body()

}