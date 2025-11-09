package com.awesome.manager.core.data.repository.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.data.extention.amInsert
import com.awesome.manager.core.data.extention.amRequest
import com.awesome.manager.core.data.extention.asUIState
import com.awesome.manager.core.common.asDateTimeString
import com.awesome.manager.core.common.currentTime
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.data.model.asNetwork
import com.awesome.manager.core.data.repository.asPagingDataFlow
import com.awesome.manager.core.database.dao.AccountDao
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.model.AmAccountWithDetails
import com.awesome.manager.core.network.datasource.AccountNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class OfflineFirstAccountRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val accountNetworkDataSource: AccountNetworkDataSource
) : AccountRepository {

    override suspend fun createAccount(
        creatorUserID: String,
        currencyID: String,
        defaultTransactionTypeID: String,
        name: String,
        imageUrl: String?,
    ) = amInsert {
        val newAccount: AccountEntity = AccountEntity(
            id = Uuid.random().toString(),
            creatorUserID = creatorUserID,
            currencyID = currencyID,
            defaultTransactionTypeID = defaultTransactionTypeID,
            name = name,
            imageUrl = imageUrl,
            pending = true,
            createdAt = currentTime(),
            updatedAt = currentTime(),
        )
        accountDao.upsertAccount(newAccount)
    }

    override suspend fun updateAccount(
        accountID: String,
        currencyID: String,
        defaultTransactionTypeID: String,
        name: String,
        imageUrl: String?,
    ) = amInsert {
        val account=accountDao.getAccountByID(accountID).first().accountEntity
        val updatedAccount: AccountEntity = AccountEntity(
            id = account.id,
            creatorUserID = account.creatorUserID,
            currencyID = currencyID,
            defaultTransactionTypeID = defaultTransactionTypeID,
            name = name,
            imageUrl = imageUrl,
            pending = true,
            createdAt = account.createdAt,
            updatedAt = currentTime(),
        )
        accountDao.upsertAccount(updatedAccount)
    }

    override fun getAccounts(searchKey: String?): Flow<PagingData<AmAccountWithDetails>> =
        { accountDao.getAccounts(searchKey) }.asPagingDataFlow { asModel() }

    override fun getAccountByID(accountID: String): Flow<AmAccountWithDetails> =
        accountDao.getAccountByID(accountID).map { it.asModel() }

    override fun refreshAccounts(): Flow<AmState<Unit>> = amRequest {
        val lastUpdateAccountTime = (accountDao.getLastUpdatedAccount()?.updatedAt ?: 0) + 1
        val lastUpdatedAccountDateTime = lastUpdateAccountTime.asDateTimeString()
        val accountsNetwork = accountNetworkDataSource
            .returnUpdatedAccounts(lastUpdatedAccountDateTime)
        accountsNetwork.map { accountDao.upsertAccount(it.asEntity()) }
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