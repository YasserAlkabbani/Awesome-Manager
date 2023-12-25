package com.awesome.manager.core.data.repository.accounts

import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.UpsertAccount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun upsertAccount(upsertAccount: UpsertAccount)

    fun returnAccounts(searchKey: String): Flow<List<AmAccount>>

    fun returnAccountById(accountId: String): Flow<AmAccount>

    suspend fun refreshAccounts()

    suspend fun syncAccount()

}