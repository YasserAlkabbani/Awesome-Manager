package com.awesome.manager.core.database.dao

import androidx.paging.PagingSource
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
    suspend fun upsertTransaction(transactionEntity: TransactionEntity)

    @Upsert
    suspend fun upsertTransaction(transactionEntity: List<TransactionEntity>)

    @Transaction
    @Query(
        "SELECT transactions.* ," +
                "(transactions.creator_user_id == users.user_id) AS update_permission ," +
                "(SELECT accounts.name FROM accounts WHERE transactions.account_id ==  accounts.account_id" + ") AS account_name ," +
                "(SELECT currencies.code FROM currencies WHERE accounts.currency_id == currencies.currency_id" + ") AS currency_code ," +
                "(SELECT currencies.symbol FROM currencies WHERE accounts.currency_id == currencies.currency_id) AS currency_symbol ," +
                "(SELECT transaction_types.is_positive FROM transaction_types WHERE transactions.transaction_type_id == transaction_types.transaction_type_id) AS is_positive, " +
                "(SELECT transaction_types.type FROM transaction_types WHERE transactions.transaction_type_id == transaction_types.transaction_type_id) AS transaction_type " +
                "FROM transactions " +
                "LEFT JOIN " +
                "accounts ON transactions.account_id==accounts.account_id, " +
                "users ON transactions.creator_user_id == users.user_id," +
                "currencies ON currencies.currency_id==accounts.currency_id, " +
                "transaction_types ON transaction_types.transaction_type_id==transaction_types.transaction_type_id " +
                "WHERE ((transactions.title LIKE '%' || :searchKey || '%') " +
                "OR (transactions.subtitle LIKE '%' || :searchKey || '%') " +
                "OR (:searchKey IS NULL)) " +
                "AND ((transactions.transaction_type_id=:transactionTypeID) OR :transactionTypeID IS NULL) " +
                "AND ((transaction_at > :fromDate) OR :fromDate IS NULL) " +
                "AND ((transaction_at < :toDate) OR :toDate IS NULL) " +
                "ORDER BY transactions.transaction_at DESC"
    )
    fun returnTransactions(
        searchKey: String?, transactionTypeID: String?, fromDate: Long?, toDate: Long?,
    ): PagingSource<Int, TransactionEntityWithData>

    @Transaction
    @Query(
        "SELECT transactions.* ," +
                "(transactions.creator_user_id == users.user_id) AS update_permission ," +
                "(SELECT accounts.name FROM accounts WHERE transactions.account_id ==  accounts.account_id" + ") AS account_name ," +
                "(SELECT currencies.code FROM currencies WHERE accounts.currency_id == currencies.currency_id" + ") AS currency_code ," +
                "(SELECT currencies.symbol FROM currencies WHERE accounts.currency_id == currencies.currency_id) AS currency_symbol ," +
                "(SELECT transaction_types.is_positive FROM transaction_types WHERE transactions.transaction_type_id == transaction_types.transaction_type_id) AS is_positive, " +
                "(SELECT transaction_types.type FROM transaction_types WHERE transactions.transaction_type_id == transaction_types.transaction_type_id) AS transaction_type " +
                "FROM transactions " +
                "LEFT JOIN " +
                "accounts ON transactions.account_id==accounts.account_id, " +
                "users ON transactions.creator_user_id == users.user_id," +
                "currencies ON currencies.currency_id==accounts.currency_id, " +
                "transaction_types ON transaction_types.transaction_type_id==transaction_types.transaction_type_id " +
                "WHERE (transactions.account_id=:accountId) " +
                "AND ((transactions.title LIKE '%' || :searchKey || '%')OR transactions.subtitle LIKE '%' || :searchKey || '%') " +
                "ORDER BY transactions.transaction_at DESC"
    )
    fun returnTransactionsByAccountId(
        accountId: String, searchKey: String,
    ): PagingSource<Int, TransactionEntityWithData>

    @Transaction
    @Query(
        "SELECT transactions.* ," +
                "(transactions.creator_user_id == users.user_id) AS update_permission ," +
                "(SELECT accounts.name FROM accounts WHERE transactions.account_id ==  accounts.account_id" + ") AS account_name ," +
                "(SELECT currencies.code FROM currencies WHERE accounts.currency_id == currencies.currency_id" + ") AS currency_code ," +
                "(SELECT currencies.symbol FROM currencies WHERE accounts.currency_id == currencies.currency_id) AS currency_symbol ," +
                "(SELECT transaction_types.is_positive FROM transaction_types WHERE transactions.transaction_type_id == transaction_types.transaction_type_id) AS is_positive, " +
                "(SELECT transaction_types.type FROM transaction_types WHERE transactions.transaction_type_id == transaction_types.transaction_type_id) AS transaction_type " +
                "FROM transactions " +
                "LEFT JOIN " +
                "accounts ON transactions.account_id==accounts.account_id, " +
                "users ON transactions.creator_user_id == users.user_id," +
                "currencies ON currencies.currency_id==accounts.currency_id, " +
                "transaction_types ON transaction_types.transaction_type_id==transaction_types.transaction_type_id " +
                "WHERE transaction_id=:transactionID"
    )
    fun returnTransactionByID(transactionID: String): Flow<TransactionEntityWithData>

    @Query("SELECT * FROM transactions WHERE pending=1")
    fun returnPendingTransaction(): Flow<TransactionEntity?>

    @Query("SELECT IFNULL(updated_at,0)+1 FROM transactions WHERE pending=0 ORDER BY updated_at DESC LIMIT 1")
    suspend fun returnLastUpdatedTransaction(): Long

    @Query("SELECT COUNT(*) FROM transactions WHERE account_id=:accountId")
    suspend fun getTransactionsCount(accountId: String): Int

    @Query("DELETE FROM transactions")
    suspend fun deleteTransactions()


}