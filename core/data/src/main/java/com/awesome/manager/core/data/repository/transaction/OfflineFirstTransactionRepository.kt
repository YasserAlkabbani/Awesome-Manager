package com.awesome.manager.core.data.repository.transaction

import androidx.paging.PagingData
import com.awesome.manager.core.common.ProcessStates
import com.awesome.manager.core.data.extention.amInsert
import com.awesome.manager.core.data.extention.amRequest
import com.awesome.manager.core.data.extention.asUIState
import com.awesome.manager.core.common.asDateTimeString
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asNetwork
import com.awesome.manager.core.database.dao.TransactionDao
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.AmTransactionWithDetails
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstTransactionRepository @Inject constructor(
    private val transactionNetworkDataSource: TransactionNetworkDataSource,
    private val transactionDao: TransactionDao,
) : TransactionRepository {

    override suspend fun upsertTransaction(transaction: AmTransaction) = amInsert {
        transactionDao.upsertTransaction(transaction.asEntity())
    }

    override fun getTransactions(
        searchKey: String?,
        transactionType: AmTransactionType?,
        fromDate: Long?,
        toDate: Long?,
    ): Flow<PagingData<AmTransactionWithDetails>> = flowOf()
//        {
//        transactionDao.returnTransactions(
//            searchKey = searchKey,
//            transactionTypeID = transactionType?.id,
//            fromDate = fromDate,
//            toDate = toDate
//        )
//    }.asPagingDataFlow(asModel = { asModel() })


    override fun getTransactionsByAccountID(
        accountId: String,
        searchKey: String,
    ): Flow<PagingData<AmTransactionWithDetails>> = flowOf()
//        {
//        transactionDao.returnTransactionsByAccountId(
//            accountId = accountId, searchKey = searchKey
//        )
//    }.asPagingDataFlow(asModel = { asModel() })

    override fun returnTransactionByID(transactionId: String): Flow<AmTransactionWithDetails> =
        flowOf()
//        transactionDao.getTransactionByID(transactionId).map { it.asModel() }

    override fun refreshTransactions() = amRequest {

        val lastUpdateTransactionTime =
            transactionDao.returnLastUpdatedTransaction().asDateTimeString()

        val transactionsEntity =
            transactionNetworkDataSource.returnUpdatedTransactions(lastUpdateTransactionTime)
                .map { it.asEntity() }

        transactionsEntity.forEach {
            transactionDao.upsertTransaction(it)
        }

    }

    override fun synTransactions(): Flow<ProcessStates<Unit>> =
        transactionDao.returnPendingTransaction()
            .filterNotNull()
            .distinctUntilChanged()
            .map { it.asNetwork() }
            .asUIState { networkTransaction ->
                transactionNetworkDataSource.upsertTransaction(networkTransaction)
            }

    override suspend fun getTransactionCount(accountId: String): Int =
        transactionDao.getTransactionsCount(accountId)

    override suspend fun deleteTransactions() = transactionDao.deleteTransactions()

}