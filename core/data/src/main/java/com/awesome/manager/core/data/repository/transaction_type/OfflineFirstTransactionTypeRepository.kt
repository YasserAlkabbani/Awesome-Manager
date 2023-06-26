package com.awesome.manager.core.data.repository.transaction_type

import android.util.Log
import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.common.amRequest
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
        val transactionTypes=transactionTypesNetworkDataSource.returnUpdatedTransactionType().map { it.asEntity() }
        transactionTypeDao.upsertTransactionType(transactionTypes)
        transactionTypes
    }.map {
        when(it){
            is AmResult.Error -> Log.d("TRANSACTION_TYPE","ERROR ${it.throwable.message}")
            is AmResult.Loading -> Log.d("TRANSACTION_TYPE","LOADING")
            is AmResult.Success -> Log.d("TRANSACTION_TYPE","SUCCESS ${it.data}")
        }
    }.collect()

    override fun returnTransactionTypes(): Flow<List<AmTransactionType>> =
        transactionTypeDao.returnTransactionTypes().map { it.map { it.asModel() } }

}