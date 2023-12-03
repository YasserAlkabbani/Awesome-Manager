package com.awesome.manager.core.data.repository.transaction

import android.util.Log
import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.common.amInsert
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.common.asAmResult
import com.awesome.manager.core.data.model.asDomain
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asNetwork
import com.awesome.manager.core.database.dao.TransactionDao
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import java.util.UUID
import javax.inject.Inject

class OfflineFirstTransactionRepository @Inject constructor(
    private val transactionNetworkDataSource: TransactionNetworkDataSource,
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun createTransaction(
        creatorUserId: String,
        accountId: String,
        transactionTypeId: String,
        title: String,
        subtitle: String,
        amount: Double,
        paymentTransaction: Boolean
    ) {
        val transactionEntity = createTransactionEntity(
            creatorUserId = creatorUserId,
            accountId = accountId,
            transactionTypeId = transactionTypeId,
            title = title,
            subtitle = subtitle,
            amount = amount,
            paymentTransaction = paymentTransaction
        )
        amInsert { transactionDao.upsertTransaction(transactionEntity) }
    }

    override suspend fun refreshTransactions() = amRequest {
        val transactions =
            transactionNetworkDataSource.returnUpdatedTransactions().map { it.asEntity() }
        transactionDao.upsertTransaction(transactions)
        transactions
    }.collect()

    override suspend fun synTransactions() {
        transactionDao.returnPendingTransaction().filterNotNull().distinctUntilChanged()
            .map { it.asNetwork() }.asAmResult(
                taskToDo = transactionNetworkDataSource::createTransaction,
                doOnSuccess = ::refreshTransactions
            ).map {
                when (it) {
                    is AmResult.Error -> Log.d(
                        "com.awesome.manager",
                        "REFRESH_TRANSACTION CREATE_STATE ERROR ${it.amError.message}"
                    )

                    is AmResult.Loading -> Log.d(
                        "com.awesome.manager",
                        "REFRESH_TRANSACTION CREATE_STATE LOADING ${it}"
                    )

                    is AmResult.Success -> Log.d(
                        "com.awesome.manager",
                        "REFRESH_TRANSACTION CREATE_STATE SUCCESS $it"
                    )
                }
            }.collect()
    }

    override fun returnTransactions(searchKey: String): Flow<List<AmTransaction>> =
        transactionDao.returnTransactions(searchKey).map { it.map { it.asDomain() } }

    override fun returnTransactionsByAccountId(
        accountId: String,
        searchKey: String
    ): Flow<List<AmTransaction>> =
        transactionDao.returnTransactionsByAccountId(accountId, searchKey)
            .map { it.map { it.asDomain() } }

    override fun returnTransactionById(id: String?): Flow<AmTransaction?> =
        transactionDao.returnTransactionById(id).map { it?.asDomain() }
}

private fun createTransactionEntity(
    creatorUserId: String,
    accountId: String,
    transactionTypeId: String,
    title: String,
    subtitle: String,
    amount: Double,
    paymentTransaction: Boolean
) =
    TransactionEntity(
        id = UUID.randomUUID().toString(),
        creatorUserId = creatorUserId,
        accountId = accountId,
        transactionTypeId = transactionTypeId,
        title = title,
        subtitle = subtitle,
        amount = amount,
        paymentTransaction = paymentTransaction,
        createdAt = Clock.System.now().toEpochMilliseconds(),
        updatedAt = Clock.System.now().toEpochMilliseconds(),
        pending = true
    )