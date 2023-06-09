package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.AccountNetwork

interface AccountNetworkDataSource {

    suspend fun returnUpdatedAccount():List<AccountNetwork>

    suspend fun createAccount(accountNetwork: AccountNetwork): AccountNetwork

}