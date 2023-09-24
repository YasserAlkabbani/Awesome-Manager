package com.awesome.manager.core.database.dao

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
    suspend fun upsertAccount(accountEntity: List<AccountEntity>)

    @Upsert
    suspend fun upsertAccount(accountEntity: AccountEntity)

    @Transaction
    @Query (
        "SELECT accounts.* ," +
        "IFNULL(SUM( IIF(transactions.payment_transaction=1, transactions.amount, 0)),0) AS incoming ," +
        "IFNULL(SUM( IIF(transactions.payment_transaction=0, transactions.amount, 0)),0) AS outgoing " +
        "FROM accounts " +
        "LEFT JOIN transactions ON accounts.account_id=transactions.account_id " +
        "WHERE accounts.name LIKE '%' || :searchKey || '%' " +
        "GROUP BY accounts.account_id "
    )
    fun returnAccounts(searchKey:String):Flow<List<AccountEntityWithData>>

    @Query("SELECT * FROM accounts WHERE pending=1")
    fun returnPendingAccount():Flow<AccountEntity?>

    @Transaction
    @Query (
        "SELECT accounts.* ," +
                "IFNULL(SUM( IIF(transactions.payment_transaction=1, transactions.amount, 0)),0) AS incoming ," +
                "IFNULL(SUM( IIF(transactions.payment_transaction=0, transactions.amount, 0)),0) AS outgoing " +
                "FROM accounts " +
                "LEFT JOIN transactions ON accounts.account_id=transactions.account_id " +
                "WHERE accounts.account_id=:accountId " +
                "GROUP BY accounts.account_id "
    )
    fun returnAccountById(accountId:String?):Flow<AccountEntityWithData?>

}