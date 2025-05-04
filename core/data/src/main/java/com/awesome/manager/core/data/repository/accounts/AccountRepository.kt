package com.awesome.manager.core.data.repository.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmAccountWithBalance
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun upsertAccount(account: AmAccount)

    fun getAccounts(searchKey: String? = null): Flow<PagingData<AmAccountWithBalance>>

    fun getAccountByID(accountID: String): Flow<AmAccountWithBalance>

    fun refreshAccounts(): Flow<AmState<List<AmAccountWithBalance>>>

    fun syncPendingAccounts(): Flow<AmState<List<AmAccountWithBalance>>>

    suspend fun deleteAllAccounts()

}