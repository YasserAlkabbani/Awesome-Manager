package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.asResult
import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import com.awesome.manager.core.network.model.AccountNetworkRequest
import com.awesome.manager.core.network.model.AccountNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.patch
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Resource("rest/v1/accounts")
private class AccountRequest {
    @Resource("")
    class ReturnAccount(
        val parent: AccountRequest = AccountRequest(),
        @SerialName("updated_at") val updatedAt: String,
        val select: String = "*"
    )
}

class KtorAccountNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AccountNetworkDataSource {

    override suspend fun returnUpdatedAccount(updatedAt: String): List<AccountNetworkResponse> =
        httpClient.get(AccountRequest.ReturnAccount(updatedAt = "gt.$updatedAt")).asResult()


    override suspend fun upsertAccount(accountNetwork: AccountNetworkRequest) {
        httpClient.post(AccountRequest()) {
            header("Prefer", "resolution=merge-duplicates")
            setBody(accountNetwork)
        }.asResult<Any>()
    }

}