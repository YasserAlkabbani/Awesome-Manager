package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.asResult
import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import com.awesome.manager.core.network.model.AccountNetwork
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Resource("rest/v1/accounts")
class AccountRequest {
    @Resource("")
    class ReturnAccount(
        val parent: AccountRequest = AccountRequest(),
        val select: String = "*"
    )
}

class KtorAccountNetworkDataSource @Inject constructor(private val httpClient: HttpClient): AccountNetworkDataSource {

    override suspend fun returnUpdatedAccount(): List<AccountNetwork> =
        httpClient.get(AccountRequest.ReturnAccount()).asResult()

    override suspend fun createAccount(accountNetwork: AccountNetwork):AccountNetwork =
        httpClient.post(AccountRequest()){ setBody(accountNetwork) }.asResult()

}