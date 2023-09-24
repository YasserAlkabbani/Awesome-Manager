package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.AccountNetworkRequest
import com.awesome.manager.core.network.model.AccountNetworkResponse

interface AccountNetworkDataSource {

    suspend fun returnUpdatedAccount(): List<AccountNetworkResponse>

    suspend fun upsertAccount(accountNetwork: AccountNetworkRequest)

}