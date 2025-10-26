package com.awesome.manager.core.data.repository.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.data.extention.amInsert
import com.awesome.manager.core.data.extention.amRequest
import com.awesome.manager.core.data.extention.asUIState
import com.awesome.manager.core.common.asDateTimeString
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.data.model.asNetwork
import com.awesome.manager.core.data.repository.asPagingDataFlow
import com.awesome.manager.core.database.dao.AccountDao
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmAccountWithDetails
import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstAccountRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val accountNetworkDataSource: AccountNetworkDataSource
) : AccountRepository {

    override suspend fun upsertAccount(account: AmAccount) = amInsert {
        accountDao.upsertAccount(account.asEntity())
    }

    override fun getAccounts(searchKey: String?): Flow<PagingData<AmAccountWithDetails>> =flowOf()
//        { accountDao.getAccounts(searchKey) }.asPagingDataFlow { asModel() }

    override fun getAccountByID(accountID: String): Flow<AmAccountWithDetails> = flowOf()
//        accountDao.getAccountByID(accountID).map { it.asModel() }

    override fun refreshAccounts(): Flow<AmState<Unit>> = amRequest {
        val lastUpdateAccountTime = (accountDao.getLastUpdatedAccount()?.updatedAt ?: 0) + 1
        val lastUpdatedAccountDateTime = lastUpdateAccountTime.asDateTimeString()
        val accountsNetwork = accountNetworkDataSource
            .returnUpdatedAccounts(lastUpdatedAccountDateTime)
        accountsNetwork.map { accountDao.upsertAccount(it.asEntity())}
    }

    override fun syncPendingAccounts(): Flow<AmState<Unit>> =
        accountDao.getPendingAccounts()
            .filterNotNull()
            .distinctUntilChanged()
            .asUIState { pendingAccountEntity ->
                val pendingAccountNetworkRequest = pendingAccountEntity.asNetwork()
                accountNetworkDataSource.upsertAccount(pendingAccountNetworkRequest)
            }

    override suspend fun deleteAllAccounts() = accountDao.deleteAccounts()

}