package com.awesome.manager.core.data.repository.transaction

import androidx.paging.PagingData
import com.awesome.manager.core.common.ProcessStates
import com.awesome.manager.core.data.extention.amInsert
import com.awesome.manager.core.data.extention.amRequest
import com.awesome.manager.core.data.extention.asUIState
import com.awesome.manager.core.common.asDateTimeString
import com.awesome.manager.core.common.currentTime
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.data.model.asNetwork
import com.awesome.manager.core.data.repository.asPagingDataFlow
import com.awesome.manager.core.database.dao.TransactionDao
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.AmTransactionWithDetails
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class OfflineFirstTransactionRepository @Inject constructor(
    private val transactionNetworkDataSource: TransactionNetworkDataSource,
    private val transactionDao: TransactionDao,
) : TransactionRepository {

    override suspend fun createTransaction(
        creatorUserID: String,
        accountID: String,
        transactionTypeID: String,
        title: String,
        subtitle: String,
        amount: Double,
        transactionAt: Long
    ) = amInsert {
        val transactionEntity: TransactionEntity = TransactionEntity(
            id = Uuid.random().toString(),
            creatorUserID = creatorUserID,
            accountID = accountID,
            transactionTypeID = transactionTypeID,
            title = title,
            subtitle = subtitle,
            amount = amount,
            transactionAt = transactionAt,
            pending = true,
            createdAt = currentTime(),
            updatedAt = currentTime(),
        )
        transactionDao.upsertTransaction(transactionEntity)
    }

    override suspend fun updateTransaction(
        transactionID: String,
        transactionTypeID: String,
        title: String,
        subtitle: String,
        amount: Double,
        transactionAt: Long
    ) = amInsert {
        val transaction =
            transactionDao.returnTransactionByID(transactionID).first().transactionEntity
        val transactionEntity: TransactionEntity = TransactionEntity(
            id = transactionID,
            creatorUserID = transaction.creatorUserID,
            accountID = transaction.accountID,
            transactionTypeID = transactionTypeID,
            title = title,
            subtitle = subtitle,
            amount = amount,
            transactionAt = transactionAt,
            pending = true,
            createdAt = transaction.createdAt,
            updatedAt = currentTime(),
        )
        transactionDao.upsertTransaction(transactionEntity)
    }


    override fun returnTransactionsByAccountID(
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

    override fun returnTransactions(
        searchKey: String?,
        transactionType: AmTransactionType?,
        fromDate: Long?,
        toDate: Long?,
    ): Flow<PagingData<AmTransactionWithDetails>> = {
        transactionDao.returnTransactions(
            searchKey = searchKey,
            transactionTypeID = transactionType?.id,
            fromDate = fromDate,
            toDate = toDate
        )
    }.asPagingDataFlow(asModel = { asModel() })


    override fun synTransactions(): Flow<ProcessStates<Unit>> =
        transactionDao.returnPendingTransaction()
            .filterNotNull()
            .distinctUntilChanged()
            .map { it.asNetwork() }
            .asUIState { networkTransaction ->
                transactionNetworkDataSource.upsertTransaction(networkTransaction)
            }

    override suspend fun returnTransactionCount(accountId: String): Int =
        transactionDao.getTransactionsCount(accountId)

    override suspend fun deleteTransactions() = transactionDao.deleteTransactions()

}