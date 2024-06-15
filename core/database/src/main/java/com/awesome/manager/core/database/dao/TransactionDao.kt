package com.awesome.manager.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.database.model.TransactionEntityWithData
import com.awesome.manager.core.database.model.TransactionTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Upsert
    suspend fun upsertTransaction(transactionEntity: TransactionEntity)

    @Upsert
    suspend fun upsertTransaction(transactionEntity: List<TransactionEntity>)


    @Transaction
    @Query(
        "SELECT * FROM transactions " +
                "WHERE ((transactions.title LIKE '%' || :searchKey || '%') " +
                "OR (transactions.subtitle LIKE '%' || :searchKey || '%') " +
                "OR (:searchKey IS NULL)) " +
                "AND ((transaction_type=:transactionType) OR :transactionType IS NULL) " +
                "AND ((transaction_at > :fromDate) OR :fromDate IS NULL) " +
                "AND ((transaction_at < :toDate) OR :toDate IS NULL) " +
                "ORDER BY transactions.transaction_at DESC"
    )
    fun returnTransactions(
        searchKey: String?, transactionType: TransactionTypeEntity?, fromDate: Long?, toDate: Long?
    ): PagingSource<Int, TransactionEntityWithData>

    @Transaction
    @Query(
        "SELECT * FROM transactions " +
                "WHERE (transactions.account_id=:accountId) " +
                "AND (transactions.title LIKE '%' || :searchKey || '%') " +
                "ORDER BY transactions.transaction_at DESC"
    )
    fun returnTransactionsByAccountId(
        accountId: String, searchKey: String
    ): PagingSource<Int, TransactionEntityWithData>

    @Transaction
    @Query("SELECT * FROM transactions WHERE transaction_id=:transactionId")
    fun returnTransactionById(transactionId: String): Flow<TransactionEntityWithData>

    @Query("SELECT * FROM transactions WHERE pending=1")
    fun returnPendingTransaction(): Flow<TransactionEntity?>

    @Query("SELECT * FROM transactions WHERE pending=0 ORDER BY updated_at DESC LIMIT 1")
    suspend fun returnLastUpdatedTransaction(): TransactionEntity?

    @Query("SELECT COUNT(*) FROM transactions WHERE account_id=:accountId")
    suspend fun returnTransactionsCount(accountId: String): Int

    @Query("DELETE FROM transactions")
    suspend fun deleteTransactions()


}