package com.awesome.manager.core.data.repository.transaction_type

import com.awesome.manager.core.common.ProcessStates
import com.awesome.manager.core.model.AmTransactionType
import kotlinx.coroutines.flow.Flow

interface TransactionTypeRepository {

    suspend fun refreshTransactionsTypes(): Flow<ProcessStates<Unit>>

    fun returnTransactionsTypes(): Flow<List<AmTransactionType>>

}