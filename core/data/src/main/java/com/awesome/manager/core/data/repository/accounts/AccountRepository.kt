package com.awesome.manager.core.data.repository.accounts

import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun refreshAccounts()

    suspend fun syncAccount()

    suspend fun createAccount(
        creatorUserId: String, currencyId: String, defaultTransactionTypeId: String,
        accountName: String, imageUrl: String,
    )

    fun returnAccounts(searchKey: String): Flow<List<AmAccount>>

    fun returnAccountById(accountId: String?): Flow<AmAccount?>

}