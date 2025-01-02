package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.request.AccountNetworkRequest
import com.awesome.manager.core.network.model.response.AccountNetworkResponse

interface AccountNetworkDataSource {

    suspend fun getUpdatedAccount(updatedAt: String): List<AccountNetworkResponse>

    suspend fun insertAccount(accountNetworkRequest: AccountNetworkRequest): List<AccountNetworkResponse>

    suspend fun updateAccount(accountNetworkRequest: AccountNetworkRequest): List<AccountNetworkResponse>

}