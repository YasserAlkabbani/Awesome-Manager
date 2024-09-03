package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import com.awesome.manager.core.network.model.AccountNetworkRequest
import com.awesome.manager.core.network.model.AccountNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import javax.inject.Inject

class KtorAccountNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AccountNetworkDataSource {

    private fun getAccountUrl(path: String = "") = "rest/v1/accounts/$path"

    override suspend fun returnUpdatedAccount(updatedAt: String): List<AccountNetworkResponse> =
        httpClient
            .get(getAccountUrl(getAccountUrl())) {
                parameter("select", "*")
                parameter("updated_at", "gt.$updatedAt")
            }.body()


    override suspend fun upsertAccount(accountNetwork: AccountNetworkRequest):Unit =
        httpClient
            .post(getAccountUrl()) {
                header("Prefer", "resolution=merge-duplicates")
                setBody(accountNetwork)
            }.body()

}