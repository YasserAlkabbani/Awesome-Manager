package com.awesome.manager.core.data.repository.transaction_type

import com.awesome.manager.core.model.AmTransactionType
import kotlinx.coroutines.flow.Flow

interface TransactionTypeRepository {

    suspend fun refreshTransactionType()

    fun returnTransactionTypes(): Flow<List<AmTransactionType>>

}