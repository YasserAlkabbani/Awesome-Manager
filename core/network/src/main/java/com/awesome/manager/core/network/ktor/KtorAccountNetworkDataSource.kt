package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.asResult
import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import com.awesome.manager.core.network.model.AccountNetworkRequest
import com.awesome.manager.core.network.model.AccountNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import javax.inject.Inject

class KtorAccountNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AccountNetworkDataSource {

    override suspend fun returnUpdatedAccount(updatedAt: String): List<AccountNetworkResponse> =
        httpClient.get(GetAccounts(updatedAt = "gt.$updatedAt")).asResult()


    override suspend fun upsertAccount(accountNetwork: AccountNetworkRequest) {
        httpClient.post(UpsertAccount()) {
            header("Prefer", "resolution=merge-duplicates")
            setBody(accountNetwork)
        }.asResult<Any>()
    }

}




private const val ACCOUNTS_URL:String="rest/v1/accounts"

@Resource(ACCOUNTS_URL)
private class UpsertAccount

@Resource(ACCOUNTS_URL)
private class GetAccounts(
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("select") val select: String = "*"
)
