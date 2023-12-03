package com.awesome.manager.core.data.repository.transaction

import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun createTransaction(
        creatorUserId: String,
        accountId: String,
        transactionTypeId: String,
        title: String,
        subtitle: String,
        amount: Double,
        paymentTransaction: Boolean
    )

    suspend fun refreshTransactions()

    suspend fun synTransactions()

    fun returnTransactions(searchKey: String): Flow<List<AmTransaction>>

    fun returnTransactionsByAccountId(accountId: String, searchKey: String): Flow<List<AmTransaction>>

    fun returnTransactionById(id: String?): Flow<AmTransaction?>

}