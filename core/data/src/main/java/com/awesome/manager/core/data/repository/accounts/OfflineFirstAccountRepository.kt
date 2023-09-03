package com.awesome.manager.core.data.repository.accounts

import android.util.Log
import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.common.amInsert
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.common.asAmResult
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.data.model.asNetwork
import com.awesome.manager.core.database.dao.AccountDao
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import java.util.UUID
import javax.inject.Inject

class OfflineFirstAccountRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val accountNetworkDataSource: AccountNetworkDataSource
) :AccountRepository{

    override suspend fun createAccount(
        creatorUserId:String, currencyId:String, defaultTransactionTypeId:String,
        accountName: String, imageUrl:String,
    ) {
        val account:AccountEntity=createAccountEntity(
            creatorUserId=creatorUserId, currencyId=currencyId,
            defaultTransactionTypeId=defaultTransactionTypeId,
            accountName=accountName, imageUrl=imageUrl,
        )
        amInsert { accountDao.upsertAccount(account) }
    }

    override suspend fun refreshAccounts() = amRequest {
        val accountsEntity=accountNetworkDataSource.returnUpdatedAccount().map { it.asEntity() }
        accountDao.upsertAccount(accountsEntity)
    }.collect()

    override suspend fun syncAccount() {
        accountDao.returnPendingAccount().distinctUntilChanged().asAmResult(
            taskToDo = {
                val accountsNetwork=it.map {accountEntity->accountEntity.asNetwork() }
                accountNetworkDataSource.createAccount(accountsNetwork)
            },
            doOnError = ::syncAccount, doOnSuccess = ::refreshAccounts
        ).map {
            when(it){
                is AmResult.Error -> Log.d("com.awesome.manager", "REFRESH_ACCOUNT CREATE_STATE ERROR ${it.amError.message}")
                is AmResult.Loading -> Log.d("com.awesome.manager","REFRESH_ACCOUNT CREATE_STATE LOADING ${it}")
                is AmResult.Success -> Log.d("com.awesome.manager", "REFRESH_ACCOUNT CREATE_STATE SUCCESS $it")
            }
        }.collect()
    }

    override fun returnAccounts(searchKey:String): Flow<List<AmAccount>> =
        accountDao.returnAccounts(searchKey).map { it.map { it.asModel() } }

    override fun returnAccountById(accountId: String?): Flow<AmAccount?> =
        accountDao.returnAccountById(accountId).map { it?.asModel() }

}

private fun createAccountEntity(
    creatorUserId:String, currencyId:String, defaultTransactionTypeId:String,
    accountName: String, imageUrl:String,
)= AccountEntity(
        id=UUID.randomUUID().toString(), creatorUserId = creatorUserId, defaultTransactionTypeId = defaultTransactionTypeId,
        currencyId = currencyId, name = accountName, imageUrl=imageUrl,
        createdAt = Clock.System.now().toEpochMilliseconds(), updatedAt = Clock.System.now().toEpochMilliseconds(),
        pending = true
    )