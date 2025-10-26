package com.awesome.manager.core.data.repository.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmAccountWithDetails
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun upsertAccount(account: AmAccount)

    fun getAccounts(searchKey: String? = null): Flow<PagingData<AmAccountWithDetails>>

    fun getAccountByID(accountID: String): Flow<AmAccountWithDetails>

    fun refreshAccounts(): Flow<AmState<Unit>>

    fun syncPendingAccounts(): Flow<AmState<Unit>>

    suspend fun deleteAllAccounts()

}