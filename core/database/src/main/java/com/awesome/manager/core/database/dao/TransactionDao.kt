package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.database.model.TransactionEntityWithData
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao{

    @Upsert
    fun upsertTransaction(transactionEntity: TransactionEntity)

    @Upsert
    fun upsertTransaction(transactionEntity: List<TransactionEntity>)

    @Query("SELECT * FROM transactions WHERE pending=1")
    fun returnPendingTransaction():Flow<List<TransactionEntity>>

    @Transaction
    @Query("SELECT * FROM transactions")
    fun returnTransactions():Flow<List<TransactionEntityWithData>>

    @Transaction
    @Query("SELECT * FROM transactions WHERE transaction_id=:transactionId")
    fun returnTransactionById(transactionId:String):Flow<TransactionEntityWithData>

}