package com.awesome.manager.core.data.repository.accounts

import com.awesome.manager.core.common.amInsert
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.common.asAmResult
import com.awesome.manager.core.common.asDateTime
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.data.model.asNetwork
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

    override suspend fun upsertAccount(upsertAccount: UpsertAccount) {
        val accountEntity: AccountEntity = upsertAccount.asEntity()
        amInsert { accountDao.upsertAccount(accountEntity) }
    }

    override fun returnAccounts(searchKey: String): Flow<List<AmAccount>> =
        accountDao.returnAccounts(searchKey).map { it.map { it.asModel() } }

    override fun returnAccountById(accountId: String): Flow<AmAccount> =
        accountDao.returnAccountById(accountId).map { it.asModel() }

    override suspend fun refreshAccounts() = amRequest {
        val lastUpdateAccountTime = (accountDao.returnLastUpdatedAccount()?.updatedAt ?: 0) + 1
        val lastUpdatedAccountDateTime = lastUpdateAccountTime.asDateTime().toString()
        val accountsEntity = accountNetworkDataSource
            .returnUpdatedAccount(lastUpdatedAccountDateTime).map { it.asEntity() }
        accountDao.upsertAccount(accountsEntity)
    }.collect()

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