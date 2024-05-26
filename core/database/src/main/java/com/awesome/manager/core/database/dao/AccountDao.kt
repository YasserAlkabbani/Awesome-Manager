package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.AccountEntityWithData
import com.awesome.manager.core.database.model.TransactionTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Upsert
    suspend fun upsertAccount(accountEntity: List<AccountEntity>)

    @Upsert
    suspend fun upsertAccount(accountEntity: AccountEntity)

    @Transaction
    @Query(
        "SELECT accounts.* ," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:income, transactions.amount, 0)),0) AS income," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:expenses, transactions.amount, 0)),0) AS expenses," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:debtor, transactions.amount, 0)),0) AS debtor," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:creditor, transactions.amount, 0)),0) AS creditor " +
                "FROM accounts " +
                "LEFT JOIN transactions ON accounts.account_id=transactions.account_id " +
                "WHERE accounts.name LIKE '%' || :searchKey || '%' " +
                "GROUP BY accounts.account_id "
    )
    fun returnAccounts(
        searchKey: String,
        income: TransactionTypeEntity = TransactionTypeEntity.INCOME,
        expenses: TransactionTypeEntity = TransactionTypeEntity.EXPENSES,
        debtor: TransactionTypeEntity = TransactionTypeEntity.DEBTOR,
        creditor: TransactionTypeEntity = TransactionTypeEntity.CREDITOR,
    ): Flow<List<AccountEntityWithData>>

    @Transaction
    @Query(
        "SELECT accounts.* ," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:income, transactions.amount, 0)),0) AS income," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:expenses, transactions.amount, 0)),0) AS expenses," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:debtor, transactions.amount, 0)),0) AS debtor," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:creditor, transactions.amount, 0)),0) AS creditor " +
                "FROM accounts " +
                "LEFT JOIN transactions ON accounts.account_id=transactions.account_id " +
                "WHERE accounts.account_id=:accountId " +
                "GROUP BY accounts.account_id "
    )
    fun returnAccountById(
        accountId: String,
        income: TransactionTypeEntity = TransactionTypeEntity.INCOME,
        expenses: TransactionTypeEntity = TransactionTypeEntity.EXPENSES,
        debtor: TransactionTypeEntity = TransactionTypeEntity.DEBTOR,
        creditor: TransactionTypeEntity = TransactionTypeEntity.CREDITOR,
    ): Flow<AccountEntityWithData>


    @Query("SELECT * FROM accounts WHERE pending=1")
    fun returnPendingAccount(): Flow<AccountEntity?>

    @Query("SELECT * FROM accounts WHERE pending=0 ORDER BY updated_at DESC LIMIT 1")
    suspend fun returnLastUpdatedAccount(): AccountEntity?

    @Query("DELETE FROM accounts")
    suspend fun deleteAccounts()

}