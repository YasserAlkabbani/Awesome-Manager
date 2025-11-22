package com.awesome.manager.core.data.repository.transaction

import androidx.paging.PagingData
import com.awesome.manager.core.common.ProcessStates
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.AmTransactionWithDetails
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun createTransaction(
        creatorUserID: String,
        accountID: String,
        transactionTypeID: String,
        title: String,
        subtitle: String,
        amount: Double,
        transactionAt: Long
    )

    suspend fun updateTransaction(
        transactionID: String,
        transactionTypeID: String,
        title: String,
        subtitle: String,
        amount: Double,
        transactionAt: Long
    )

    fun returnTransactionByID(transactionId: String): Flow<AmTransactionWithDetails>

    fun returnTransactionsByAccountID(
        accountId: String, searchKey: String,
    ): Flow<PagingData<AmTransactionWithDetails>>

    fun returnTransactions(
        searchKey: String? = "",
        transactionType: AmTransactionType? = null,
        fromDate: Long? = null,
        toDate: Long? = null,
    ): Flow<PagingData<AmTransactionWithDetails>>


    suspend fun returnTransactionCount(accountId: String): Int

    fun refreshTransactions(): Flow<ProcessStates<Unit>>

    fun synTransactions(): Flow<ProcessStates<Unit>>

    suspend fun deleteTransactions()

}