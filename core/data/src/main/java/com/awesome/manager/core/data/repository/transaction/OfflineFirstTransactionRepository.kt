package com.awesome.manager.core.data.repository.transaction

import androidx.paging.PagingData
import com.awesome.manager.core.data.extention.amInsert
import com.awesome.manager.core.data.extention.amRequest
import com.awesome.manager.core.data.extention.asAmResult
import com.awesome.manager.core.common.asDateTimeString
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asNetwork
import com.awesome.manager.core.data.repository.asPagingDataFlow
import com.awesome.manager.core.database.dao.TransactionDao
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class OfflineFirstTransactionRepository @Inject constructor(
    private val transactionNetworkDataSource: TransactionNetworkDataSource,
    private val transactionDao: TransactionDao,
) : TransactionRepository {

    override suspend fun upsertTransaction(upsertTransaction: UpsertTransaction) = amInsert {
        val transactionEntity = upsertTransaction.asEntity()
        transactionDao.upsertTransaction(transactionEntity = transactionEntity)
    }

    override fun returnTransactions(
        searchKey: String?,
        transactionType: AmTransactionType?,
        fromDate: Long?,
        toDate: Long?,
    ): Flow<PagingData<AmTransaction>> = {
        transactionDao.returnTransactions(
            searchKey = searchKey, transactionType = transactionType?.name,
            fromDate = fromDate, toDate = toDate
        )
    }.asPagingDataFlow(asModel = { asModel() })


    override fun returnTransactionsByAccountID(
        accountId: String,
        searchKey: String,
    ): Flow<PagingData<AmTransaction>> = {
        transactionDao.returnTransactionsByAccountId(
            accountId = accountId, searchKey = searchKey
        )
    }.asPagingDataFlow(asModel = { asModel() })

    override fun returnTransactionById(transactionId: String): Flow<AmTransaction> =
        transactionDao.returnTransactionById(transactionId).map { it.asModel() }

    override fun refreshTransactions() = amRequest {
        val lastUpdateTransactionTime =
            (transactionDao.returnLastUpdatedTransaction()?.updatedAt ?: 0) + 1
        val lastUpdatedTransactionDateTime = lastUpdateTransactionTime.asDateTimeString()

        val transactionsNetwork =
            transactionNetworkDataSource.returnUpdatedTransactions(lastUpdatedTransactionDateTime)
        val transactionsEntity = transactionsNetwork.map { it.asEntity() }
        transactionDao.upsertTransaction(transactionsEntity)
    }

    override suspend fun synTransactions() {
        transactionDao.returnPendingTransaction().filterNotNull().distinctUntilChanged()
            .map { it.asNetwork() }.asAmResult(
                taskToDo = transactionNetworkDataSource::upsertTransaction,
                doOnSuccess = ::refreshTransactions
            ).collect()
    }

    override suspend fun returnTransactionCount(accountId: String): Int =
        transactionDao.returnTransactionsCount(accountId)

    override suspend fun deleteTransactions() =
        transactionDao.deleteTransactions()

}