package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.request.AccountNetworkRequest
import com.awesome.manager.core.network.model.response.AccountNetworkResponse

interface AccountNetworkDataSource {

    suspend fun returnUpdatedAccounts(updatedAt: String): List<AccountNetworkResponse>

    suspend fun upsertAccount(accountNetworkRequest: AccountNetworkRequest)

}