package com.awesome.manager.core.data.repository.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.UpsertAccount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun upsertAccount(upsertAccount: UpsertAccount)

    fun getAccounts(searchKey: String? = null): Flow<PagingData<AmAccount>>

    fun getAccountByID(accountID: String): Flow<AmAccount>

    fun refreshAccounts(): Flow<AmState<List<AmAccount>>>

    fun syncPendingAccounts(): Flow<AmState<List<AmAccount>>>

    suspend fun deleteAllAccounts()

}