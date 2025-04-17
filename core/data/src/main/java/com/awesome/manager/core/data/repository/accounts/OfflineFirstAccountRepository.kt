package com.awesome.manager.core.data.repository.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.data.extention.amInsert
import com.awesome.manager.core.data.extention.requestUIState
import com.awesome.manager.core.data.extention.asUIState
import com.awesome.manager.core.common.asDateTimeString
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.data.model.asNetwork
import com.awesome.manager.core.data.repository.asPagingDataFlow
import com.awesome.manager.core.database.dao.AccountDao
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.UpsertAccount
import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstAccountRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val accountNetworkDataSource: AccountNetworkDataSource
) : AccountRepository {

    override suspend fun upsertAccount(upsertAccount: UpsertAccount) = amInsert {
        upsertAccount.asEntity().upsert()
    }

    override fun getAccounts(searchKey: String?): Flow<PagingData<AmAccount>> =
        { accountDao.getAccounts(searchKey) }.asPagingDataFlow { asModel() }

    override fun getAccountByID(accountID: String): Flow<AmAccount> =
        accountDao.getAccountByID(accountID).map { it.asModel() }

    override fun refreshAccounts(): Flow<AmState<List<AmAccount>>> = requestUIState {
        val lastUpdateAccountTime = (accountDao.getLastUpdatedAccount()?.updatedAt ?: 0) + 1
        val lastUpdatedAccountDateTime = lastUpdateAccountTime.asDateTimeString()
        val accountsNetwork = accountNetworkDataSource
            .getUpdatedAccount(lastUpdatedAccountDateTime)
        accountsNetwork.map { it.asEntity().upsert() }
    }

    override fun syncPendingAccounts(): Flow<AmState<List<AmAccount>>> =
        accountDao.getPendingAccounts()
            .filterNotNull()
            .distinctUntilChanged()
            .asUIState { pendingAccountEntity ->
                val pendingAccountNetworkRequest = pendingAccountEntity.asNetwork()
                val accountNetworkResponse = when (pendingAccountEntity.alreadyOnNetwork) {
                    true -> accountNetworkDataSource.updateAccount(pendingAccountNetworkRequest)
                    false -> accountNetworkDataSource.insertAccount(pendingAccountNetworkRequest)
                }
                accountNetworkResponse.map { it.asEntity().upsert() }
            }

    override suspend fun deleteAllAccounts() = accountDao.deleteAccounts()

    private suspend fun AccountEntity.upsert(): AmAccount {
        accountDao.upsertAccount(this)
        return accountDao.getAccountByID(id).first().asModel()
    }

}