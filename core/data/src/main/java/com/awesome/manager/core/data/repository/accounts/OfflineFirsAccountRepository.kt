package com.awesome.manager.core.data.repository.accounts

import com.awesome.manager.core.common.amInsert
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.database.dao.AccountDao
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import com.awesome.manager.core.network.model.AccountNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineFirsAccountRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val accountNetworkDataSource: AccountNetworkDataSource
) :AccountRepository{

    override suspend fun refreshAccounts() = amRequest {
        val accountsEntity=accountNetworkDataSource.returnUpdatedAccount().map { it.asEntity() }
        accountDao.upsertAccount(accountsEntity)
        accountsEntity
    }.collect()

    override suspend fun createAccount(account: AmAccount) {
        amInsert { accountDao.upsertAccount(account.asEntity()) }
    }

    override suspend fun syncAccount() {

    }

    override fun returnAccounts(): Flow<List<AmAccount>> =
        accountDao.returnAccounts().map { it.map { it.asModel() } }

    override fun returnAccountById(accountId: String): Flow<AmAccount> =
        accountDao.returnAccountById(accountId).map { it.asModel() }

}