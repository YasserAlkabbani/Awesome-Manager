package com.awesome.manager.core.data.repository.transaction

import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun refreshTransactions()

    fun returnTransactions():Flow<List<AmTransaction>>

    fun returnTransactionById(id:String) :Flow<AmTransaction>

}