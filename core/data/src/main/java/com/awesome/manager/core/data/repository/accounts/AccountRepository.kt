package com.awesome.manager.core.data.repository.accounts

import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun refreshAccounts()

    suspend fun createAccount(account: AmAccount)

    suspend fun syncAccount(){}

    suspend fun upsertAccount(vararg accountEntity: AccountEntity)

    fun returnAccounts(): Flow<List<AmAccount>>

    fun resturnAccountById():AmAccount

}