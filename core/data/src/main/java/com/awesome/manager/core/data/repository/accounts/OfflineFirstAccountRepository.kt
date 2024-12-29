package com.awesome.manager.core.data.repository.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.data.extention.amInsert
import com.awesome.manager.core.data.extention.amRequest
import com.awesome.manager.core.data.extention.asAmResult
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstAccountRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val accountNetworkDataSource: AccountNetworkDataSource
) : AccountRepository {

    override suspend fun upsertAccount(upsertAccount: UpsertAccount) = amInsert {
        val accountEntity: AccountEntity = upsertAccount.asEntity()
        accountDao.upsertAccount(accountEntity)
    }

    override fun returnAccounts(searchKey: String?): Flow<PagingData<AmAccount>> =
        { accountDao.returnAccounts(searchKey) }.asPagingDataFlow { asModel() }

    override fun returnAccountById(accountID: String): Flow<AmAccount?> =
        accountDao.returnAccountById(accountID).map { it?.asModel() }

    override fun refreshAccounts(): Flow<AmUIState<Unit>> = amRequest {
        val lastUpdateAccountTime = (accountDao.returnLastUpdatedAccount()?.updatedAt ?: 0) + 1
        val lastUpdatedAccountDateTime = lastUpdateAccountTime.asDateTimeString()
        val accountsNetwork = accountNetworkDataSource
            .returnUpdatedAccount(lastUpdatedAccountDateTime)
        val accountsEntity = accountsNetwork.map { it.asEntity() }
        accountDao.upsertAccount(accountsEntity)
    }

    override suspend fun syncAccount() {
        accountDao.returnPendingAccount().filterNotNull().distinctUntilChanged()
            .map { it.asNetwork() }
            .asAmResult(
                taskToDo = accountNetworkDataSource::upsertAccount,
                doOnSuccess = ::refreshAccounts
            ).collect()
    }

    override suspend fun deleteAccounts() = accountDao.deleteAccounts()

}