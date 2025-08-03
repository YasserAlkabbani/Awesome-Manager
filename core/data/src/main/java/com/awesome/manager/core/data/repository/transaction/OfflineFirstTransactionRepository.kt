package com.awesome.manager.core.data.repository.transaction

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.data.extention.amInsert
import com.awesome.manager.core.data.extention.requestUIState
import com.awesome.manager.core.data.extention.asUIState
import com.awesome.manager.core.common.asDateTimeString
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asNetwork
import com.awesome.manager.core.data.repository.asPagingDataFlow
import com.awesome.manager.core.database.dao.TransactionDao
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstTransactionRepository @Inject constructor(
    private val transactionNetworkDataSource: TransactionNetworkDataSource,
    private val transactionDao: TransactionDao,
) : TransactionRepository {

    override suspend fun upsertTransaction(upsertTransaction: UpsertTransaction) = amInsert {
        upsertTransaction.asEntity().upsert()
    }

    override fun getTransactions(
        searchKey: String?,
        transactionType: AmTransactionType?,
        fromDate: Long?,
        toDate: Long?,
    ): Flow<PagingData<AmTransaction>> = {
        transactionDao.returnTransactions(
            searchKey = searchKey,
            transactionType = transactionType?.name,
            fromDate = fromDate,
            toDate = toDate
        )
    }.asPagingDataFlow(asModel = { asModel() })


    override fun getTransactionsByAccountID(
        accountId: String,
        searchKey: String,
    ): Flow<PagingData<AmTransaction>> = {
        transactionDao.returnTransactionsByAccountId(
            accountId = accountId, searchKey = searchKey
        )
    }.asPagingDataFlow(asModel = { asModel() })

    override fun getTransactionById(transactionId: String): Flow<AmTransaction> =
        transactionDao.getTransactionByID(transactionId).map { it.asModel() }

    override fun refreshTransactions() = requestUIState {
        val lastUpdateTransactionTime =
            (transactionDao.returnLastUpdatedTransaction()?.updatedAt ?: 0) + 1
        val lastUpdatedTransactionDateTime = lastUpdateTransactionTime.asDateTimeString()

        val transactionsNetwork =
            transactionNetworkDataSource.returnUpdatedTransactions(lastUpdatedTransactionDateTime)
        transactionsNetwork.map { it.asEntity().upsert() }
    }

    override fun synTransactions(): Flow<AmState<List<AmTransaction>>> =
        transactionDao.returnPendingTransaction()
            .filterNotNull()
            .distinctUntilChanged()
            .asUIState { pendingTransactionEntity ->
                val pendingAccountNetworkRequest = pendingTransactionEntity.asNetwork()
                val accountNetworkResponse = when (pendingTransactionEntity.alreadyOnNetwork) {
                    true -> transactionNetworkDataSource.updateTransaction(
                        pendingAccountNetworkRequest
                    )

                    false -> transactionNetworkDataSource.insertTransaction(
                        pendingAccountNetworkRequest
                    )
                }
                accountNetworkResponse.map { it.asEntity().upsert() }
            }


    override suspend fun getTransactionCount(accountId: String): Int =
        transactionDao.getTransactionsCount(accountId)

    override suspend fun deleteTransactions() = transactionDao.deleteTransactions()

    private suspend fun TransactionEntity.upsert(): AmTransaction {
        transactionDao.upsertTransaction(this)
        return transactionDao.getTransactionByID(id).first().asModel()
    }

}