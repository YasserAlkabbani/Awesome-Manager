package com.awesome.manager.core.data.repository.transactions

import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.data.model.asDomain
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.database.dao.TransactionDao
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import com.awesome.manager.core.network.model.TransactionNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstTransactionRepository @Inject constructor(
    private val transactionNetworkDataSource: TransactionNetworkDataSource,
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun refreshTransactions() = amRequest {
        val transactions =transactionNetworkDataSource.returnUpdatedTransactions().map { it.asEntity() }
        transactionDao.upsertTransaction(transactions)
        transactions
    }.collect()

    override fun returnTransactions(): Flow<List<AmTransaction>> =
        transactionDao.returnTransactions().map { it.map { it.asDomain() } }

    override fun retrunTransactionById(id: String): Flow<AmTransaction> =
        transactionDao.returnTransactionById(id).map { it.asDomain() }
}