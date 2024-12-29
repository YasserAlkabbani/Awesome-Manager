package com.awesome.manager.core.data.repository.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.UpsertAccount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun upsertAccount(upsertAccount: UpsertAccount)

    fun returnAccounts(searchKey: String? = null): Flow<PagingData<AmAccount>>

    fun returnAccountById(accountID: String): Flow<AmAccount?>

    fun refreshAccounts(): Flow<AmUIState<Unit>>

    suspend fun syncAccount()

    suspend fun deleteAccounts()

}