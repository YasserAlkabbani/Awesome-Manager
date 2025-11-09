package com.awesome.manager.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.AccountEntityWithData
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Upsert
    suspend fun upsertAccounts(accountEntity: List<AccountEntity>)

    @Upsert
    suspend fun upsertAccount(accountEntity: AccountEntity)

    @Transaction
    @Query(
        "WITH " +
                "income_type AS (SELECT transaction_type_id AS id FROM transaction_types WHERE type='income'), " +
                "expenses_type AS (SELECT transaction_type_id AS id FROM transaction_types WHERE type='expenses'), " +
                "debtor_type AS (SELECT transaction_type_id AS id FROM transaction_types WHERE type='debtor'), " +
                "creditor_type AS (SELECT transaction_type_id AS id FROM transaction_types WHERE type='creditor') " +
                "SELECT accounts.* ," +
                "(accounts.creator_user_id == users.user_id) AS update_permission, " +
                "currencies.code AS currency_code, " +
                "currencies.symbol AS currency_symbol, " +
                "SUM(CASE WHEN transactions.transaction_type_id == (SELECT id FROM income_type) THEN transactions.amount ELSE 0 END) AS income," +
                "SUM(CASE WHEN transactions.transaction_type_id == (SELECT id FROM expenses_type) THEN transactions.amount ELSE 0 END) AS expenses," +
                "SUM(CASE WHEN transactions.transaction_type_id == (SELECT id FROM debtor_type) THEN transactions.amount ELSE 0 END) AS debtor," +
                "SUM(CASE WHEN transactions.transaction_type_id == (SELECT id FROM creditor_type) THEN transactions.amount ELSE 0 END) AS creditor " +
                "FROM accounts " +
                "LEFT JOIN " +
                "users ON accounts.creator_user_id = users.user_id, " +
                "currencies ON accounts.currency_id = currencies.currency_id, " +
                "transactions ON accounts.account_id = transactions.account_id " +
                "WHERE ((accounts.name LIKE '%' || :searchKey || '%') OR :searchKey is NULL) " +
                "GROUP BY accounts.account_id"
    )
    fun getAccounts(searchKey: String?): PagingSource<Int, AccountEntityWithData>

    @Transaction
    @Query(
        "WITH " +
                "income_type AS (SELECT transaction_type_id AS id FROM transaction_types WHERE type='income'), " +
                "expenses_type AS (SELECT transaction_type_id AS id FROM transaction_types WHERE type='expenses'), " +
                "debtor_type AS (SELECT transaction_type_id AS id FROM transaction_types WHERE type='debtor'), " +
                "creditor_type AS (SELECT transaction_type_id AS id FROM transaction_types WHERE type='creditor') " +
                "SELECT accounts.* ," +
                "(accounts.creator_user_id == users.user_id) AS update_permission, " +
                "currencies.code AS currency_code, " +
                "currencies.symbol AS currency_symbol, " +
                "SUM(CASE WHEN transactions.transaction_type_id == (SELECT id FROM income_type) THEN transactions.amount ELSE 0 END) AS income," +
                "SUM(CASE WHEN transactions.transaction_type_id == (SELECT id FROM expenses_type) THEN transactions.amount ELSE 0 END) AS expenses," +
                "SUM(CASE WHEN transactions.transaction_type_id == (SELECT id FROM debtor_type) THEN transactions.amount ELSE 0 END) AS debtor," +
                "SUM(CASE WHEN transactions.transaction_type_id == (SELECT id FROM creditor_type) THEN transactions.amount ELSE 0 END) AS creditor " +
                "FROM accounts " +
                "LEFT JOIN " +
                "users ON accounts.creator_user_id = users.user_id, " +
                "currencies ON accounts.currency_id = currencies.currency_id, " +
                "transactions ON accounts.account_id = transactions.account_id " +
                "WHERE accounts.account_id=:accountId " +
                "GROUP BY accounts.account_id"
    )
    fun getAccountByID(accountId: String): Flow<AccountEntityWithData>


    @Query("SELECT * FROM accounts WHERE pending=1")
    fun getPendingAccounts(): Flow<AccountEntity?>

    @Query("SELECT * FROM accounts WHERE pending=0 ORDER BY updated_at DESC LIMIT 1")
    suspend fun getLastUpdatedAccount(): AccountEntity?

    @Query("DELETE FROM accounts")
    suspend fun deleteAccounts()

}