package com.awesome.manager.core.data.repository.transaction_type

import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.common.asDateTimeString
import com.awesome.manager.core.data.extention.amRequest
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.database.dao.TransactionTypeDao
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.network.datasource.TransactionTypeNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstTransactionTypeRepository @Inject constructor(
    private val transactionTypeNetworkDataSource: TransactionTypeNetworkDataSource,
    private val transactionTypeDao: TransactionTypeDao
) : TransactionTypeRepository {

    override suspend fun refreshTransactionsTypes(): Flow<AmState<Unit>> = amRequest {
        val transactionsTypesEntity =
            transactionTypeNetworkDataSource
                .returnUpdatedTransactionsTypes(0L.asDateTimeString())
                .map { it.asEntity() }
        transactionTypeDao.upsertTransactionsTypes(transactionsTypesEntity)
    }

    override fun returnTransactionsTypes(): Flow<List<AmTransactionType>> =
        transactionTypeDao.returnTransactionsTypes().map { it.map { it.asModel() } }


}