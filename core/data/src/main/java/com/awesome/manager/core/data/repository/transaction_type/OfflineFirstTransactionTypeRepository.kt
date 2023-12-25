package com.awesome.manager.core.data.repository.transaction_type

import android.util.Log
import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.common.asDateTime
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.database.dao.TransactionTypeDao
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.network.datasource.TransactionTypeNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstTransactionTypeRepository @Inject constructor(
    private val transactionTypesNetworkDataSource: TransactionTypeNetworkDataSource,
    private val transactionTypeDao: TransactionTypeDao
) : TransactionTypeRepository {

    override suspend fun refreshTransactionType() = amRequest {
        val lastUpdateTransactionTypeTime =
            (transactionTypeDao.returnLastUpdatedTransactionType()?.updatedAt ?: 0) + 1
        val lastUpdatedTransactionTypeDateTime =
            lastUpdateTransactionTypeTime.asDateTime().toString()
        val transactionTypes = transactionTypesNetworkDataSource
            .returnUpdatedTransactionType(lastUpdatedTransactionTypeDateTime).map { it.asEntity() }
        transactionTypeDao.upsertTransactionType(transactionTypes)
    }.collect()

    override fun returnTransactionTypes(): Flow<List<AmTransactionType>> =
        transactionTypeDao.returnTransactionTypes().map { it.map { it.asModel() } }

    override fun returnTransactionTypeById(transactionTypeId: String): Flow<AmTransactionType> =
        transactionTypeDao.returnTransactionTypeById(transactionTypeId).map { it.asModel() }

}