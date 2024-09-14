package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.request.AccountNetworkRequest
import com.awesome.manager.core.network.model.response.AccountNetworkResponse

interface AccountNetworkDataSource {

    suspend fun returnUpdatedAccount(updatedAt: String): List<AccountNetworkResponse>

    suspend fun upsertAccount(accountNetwork: AccountNetworkRequest): Any

}