package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.TransactionTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionTypeDao {

    @Upsert
    fun upsertTransactionType(currencyEntity: List<TransactionTypeEntity>)

    @Query("SELECT * FROM transaction_types")
    fun returnTransactionTypes(): Flow<List<TransactionTypeEntity>>

    @Query("SELECT * FROM transaction_types WHERE transaction_type_id=:transactionTypeId")
    fun returnTransactionTypeById(transactionTypeId: String): Flow<TransactionTypeEntity>

    @Query("SELECT * FROM transaction_types ORDER BY updated_at DESC LIMIT 1")
    fun returnLastUpdatedTransactionType(): TransactionTypeEntity?

}