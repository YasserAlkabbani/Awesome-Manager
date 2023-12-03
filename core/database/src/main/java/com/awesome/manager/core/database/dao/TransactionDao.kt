package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.database.model.TransactionEntityWithData
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Upsert
    fun upsertTransaction(transactionEntity: TransactionEntity)

    @Upsert
    fun upsertTransaction(transactionEntity: List<TransactionEntity>)

    @Query("SELECT * FROM transactions WHERE pending=1")
    fun returnPendingTransaction(): Flow<TransactionEntity?>

    @Transaction
    @Query("SELECT * FROM transactions WHERE transactions.title LIKE '%' || :searchKey || '%' ")
    fun returnTransactions(searchKey: String): Flow<List<TransactionEntityWithData>>

    @Transaction
    @Query("SELECT * FROM transactions WHERE transactions.account_id=:accountId AND transactions.title LIKE '%' || :searchKey || '%'")
    fun returnTransactionsByAccountId(accountId: String, searchKey: String): Flow<List<TransactionEntityWithData>>

    @Transaction
    @Query("SELECT * FROM transactions WHERE transaction_id=:transactionId")
    fun returnTransactionById(transactionId: String?): Flow<TransactionEntityWithData?>

}