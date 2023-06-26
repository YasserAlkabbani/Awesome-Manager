package com.awesome.manager.core.data.repository.transactions

import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun refreshTransactions()

    fun returnTransactions():Flow<List<AmTransaction>>

    fun retrunTransactionById(id:String) :Flow<AmTransaction>

}