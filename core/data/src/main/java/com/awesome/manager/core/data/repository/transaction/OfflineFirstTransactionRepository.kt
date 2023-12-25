package com.awesome.manager.core.data.repository.transaction

import com.awesome.manager.core.common.amInsert
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.common.asAmResult
import com.awesome.manager.core.common.asDateTime
import com.awesome.manager.core.data.model.asDomain
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asNetwork
import com.awesome.manager.core.database.dao.TransactionDao
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.UpsertTransaction
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstTransactionRepository @Inject constructor(
    private val transactionNetworkDataSource: TransactionNetworkDataSource,
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun upsertTransaction(upsertTransaction: UpsertTransaction) {
        val transactionEntity = upsertTransaction.asEntity()
        amInsert { transactionDao.upsertTransaction(transactionEntity) }
    }

    override fun returnTransactions(searchKey: String): Flow<List<AmTransaction>> =
        transactionDao.returnTransactions(searchKey).map { it.map { it.asDomain() } }

    override fun returnTransactionsByAccountId(
        accountId: String,
        searchKey: String
    ): Flow<List<AmTransaction>> =
        transactionDao.returnTransactionsByAccountId(accountId, searchKey)
            .map { it.map { it.asDomain() } }

    override fun returnTransactionById(transactionId: String): Flow<AmTransaction> =
        transactionDao.returnTransactionById(transactionId).map { it.asDomain() }

    override suspend fun refreshTransactions() = amRequest {
        val lastUpdateTransactionTime =
            (transactionDao.returnLastUpdatedTransaction()?.updatedAt ?: 0) + 1
        val lastUpdatedTransactionDateTime = lastUpdateTransactionTime.asDateTime().toString()
        val transactions =
            transactionNetworkDataSource.returnUpdatedTransactions(lastUpdatedTransactionDateTime)
                .map { it.asEntity() }
        transactionDao.upsertTransaction(transactions)
    }.collect()

    override suspend fun synTransactions() {
        transactionDao.returnPendingTransaction().filterNotNull().distinctUntilChanged()
            .map { it.asNetwork() }.asAmResult(
                taskToDo = transactionNetworkDataSource::upsertTransaction,
                doOnSuccess = ::refreshTransactions
            ).collect()
    }


}