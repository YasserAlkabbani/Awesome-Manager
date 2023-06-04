package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.awesome.manager.core.database.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao{

    @Upsert
    fun upsertTransaction(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM transactions")
    fun returnTransactions():Flow<TransactionEntity>


}