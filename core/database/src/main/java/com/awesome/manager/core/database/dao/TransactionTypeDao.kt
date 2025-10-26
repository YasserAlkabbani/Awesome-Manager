package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.awesome.manager.core.database.model.TransactionTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionTypeDao {


    @Upsert
    suspend fun upsertTransactionsTypes(transactionTypeEntity: List<TransactionTypeEntity>)

    @Query("SELECT * FROM transaction_types")
    fun returnTransactionsTypes(): Flow<List<TransactionTypeEntity>>


}