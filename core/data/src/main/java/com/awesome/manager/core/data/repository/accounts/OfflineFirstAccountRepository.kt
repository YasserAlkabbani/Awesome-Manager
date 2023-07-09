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

    override suspend fun refreshAccounts() = amRequest {
        val accountsEntity=accountNetworkDataSource.returnUpdatedAccount().map { it.asEntity() }
        accountDao.upsertAccount(accountsEntity)
        accountsEntity
    }.collect()

    override suspend fun syncAccount() {
        accountDao.returnPendingAccount().distinctUntilChanged().map {
            Log.d("com.awesome.manager","REFRESH_ACCOUNT RETURN_PENDING $it")
            val accountsNetwork=it.map {accountEntity->accountEntity.asNetwork() }
            accountNetworkDataSource.createAccount(accountsNetwork)
            Log.d("com.awesome.manager","REFRESH_ACCOUNT CREATE_SUCCESS $it")
        }.asAmResult().map {
            when(it){
                is AmResult.Error -> {
                    Log.d(
                        "com.awesome.manager",
                        "REFRESH_ACCOUNT CREATE_STATE ERROR ${it.throwable.message}"
                    )
                    delay(60000)
                    syncAccount()
                }
                is AmResult.Loading -> Log.d("com.awesome.manager","REFRESH_ACCOUNT CREATE_STATE LOADING ${it}")
                is AmResult.Success -> {
                    Log.d("com.awesome.manager", "REFRESH_ACCOUNT CREATE_STATE SUCCESS $it")
                    refreshAccounts()
                }
            }
        }.collect()
    }

    override suspend fun createAccount(
        creatorUserId:String, currencyId:String, defaultTransactionTypeId:String,
        accountName: String, imageUrl:String,
    ) {
        val account:AccountEntity=AccountEntity(
            id=UUID.randomUUID().toString(),
            creatorUserId = creatorUserId,
            defaultTransactionTypeId = defaultTransactionTypeId,
            currencyId = currencyId,
            name = accountName,
            imageUrl=imageUrl,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            pending = true
        )
        amInsert { accountDao.upsertAccount(account) }
    }



    override fun returnAccounts(): Flow<List<AmAccount>> =
        accountDao.returnAccounts().map { it.map { it.asModel() } }

    override fun returnAccountById(accountId: String): Flow<AmAccount> =
        accountDao.returnAccountById(accountId).map { it.asModel() }

}