package com.awesome.manager.core.data.repository.transaction

import com.awesome.manager.core.common.amInsert
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.data.model.asDomain
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.database.dao.TransactionDao
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import java.util.UUID
import javax.inject.Inject

class OfflineFirstTransactionRepository @Inject constructor(
    private val transactionNetworkDataSource: TransactionNetworkDataSource,
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun createTransaction(
        creatorUserId:String,
        accountId: String,
        transactionTypeId: String,
        title: String,
        subtitle: String,
        amount: Double,
        paymentTransaction:Boolean
    ) {
        val transactionEntity=createTransactionEntity(
            creatorUserId=creatorUserId, accountId=accountId, transactionTypeId = transactionTypeId,
            title=title, subtitle=subtitle, amount=amount,paymentTransaction = paymentTransaction
        )
        amInsert { transactionDao.upsertTransaction(transactionEntity) }
    }

    override suspend fun refreshTransactions() = amRequest {
        val transactions =transactionNetworkDataSource.returnUpdatedTransactions().map { it.asEntity() }
        transactionDao.upsertTransaction(transactions)
        transactions
    }.collect()

    override fun returnTransactions(): Flow<List<AmTransaction>> =
        transactionDao.returnTransactions().map { it.map { it.asDomain() } }

    override fun returnTransactionById(id: String): Flow<AmTransaction> =
        transactionDao.returnTransactionById(id).map { it.asDomain() }
}

private fun createTransactionEntity(
    creatorUserId:String,
    accountId: String,
    transactionTypeId:String,
    title: String,
    subtitle: String,
    amount: Double,
    paymentTransaction:Boolean
)=
    TransactionEntity(
        id= UUID.randomUUID().toString(),
        creatorUserId=creatorUserId,
        accountId=accountId,
        transactionTypeId=transactionTypeId,
        title=title,
        subtitle=subtitle,
        amount=amount,
        paymentTransaction=paymentTransaction,
        createdAt=Clock.System.now().toEpochMilliseconds(),
        updatedAt= Clock.System.now().toEpochMilliseconds(),
        pending = true
    )