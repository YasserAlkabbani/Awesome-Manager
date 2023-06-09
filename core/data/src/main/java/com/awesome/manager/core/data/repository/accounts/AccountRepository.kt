package com.awesome.manager.core.data.repository.accounts

import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun refreshAccounts(): Flow<AmResult<List<AccountEntity>>>

    suspend fun createAccount(account: AmAccount)

    suspend fun syncAccount()

    fun returnAccounts(): Flow<List<AmAccount>>

    fun returnAccountById(accountId:String): Flow<AmAccount>

}