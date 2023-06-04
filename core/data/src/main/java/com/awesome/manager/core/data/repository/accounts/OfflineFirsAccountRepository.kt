package com.awesome.manager.core.data.repository.accounts

import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.database.dao.AccountDao
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirsAccountRepository @Inject constructor(
    private val accountDao: AccountDao
) :AccountRepository{

    override suspend fun upsertAccount(vararg accountEntity: AccountEntity)=
        accountDao.upsertAccount(*accountEntity)

    override fun returnAccounts(): Flow<List<AmAccount>> =
        accountDao.returnAccounts().map { it.map { it.asModel() } }

}