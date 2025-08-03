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
    suspend fun upsertAccount(accountEntity: List<AccountEntity>)

    @Upsert
    suspend fun upsertAccount(accountEntity: AccountEntity)

    @Transaction
    @Query(
        "SELECT accounts.* ," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type=='INCOME' THEN transactions.amount ELSE 0 END ),0) AS income," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type=='EXPENSES' THEN transactions.amount ELSE 0 END ),0) AS expenses," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type=='DEBTOR' THEN transactions.amount ELSE 0 END ),0) AS debtor," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type=='CREDITOR' THEN transactions.amount ELSE 0 END ),0) AS creditor, " +
                "(accounts.creator_user_id == users.user_id) AS update_permission " +
                "FROM accounts,users " +
                "LEFT JOIN transactions ON accounts.account_id=transactions.account_id " +
                "WHERE ((accounts.name LIKE '%' || :searchKey || '%') OR :searchKey is NULL) " +
                "GROUP BY accounts.name "
    )
    fun getAccounts(searchKey: String?): PagingSource<Int, AccountEntityWithData>

    @Transaction
    @Query(
        "SELECT accounts.* ," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type='INCOME' THEN transactions.amount ELSE 0 END),0) AS income," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type='EXPENSES' THEN transactions.amount ELSE 0 END),0) AS expenses," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type='DEBTOR' THEN transactions.amount ELSE 0 END),0) AS debtor," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type='CREDITOR' THEN transactions.amount ELSE 0 END),0) AS creditor, " +
                "(accounts.creator_user_id == users.user_id) AS update_permission " +
                "FROM accounts,users " +
                "LEFT JOIN transactions ON accounts.account_id=transactions.account_id " +
                "WHERE accounts.account_id=:accountId " +
                "GROUP BY accounts.name "
    )
    fun getAccountByID(accountId: String): Flow<AccountEntityWithData>


    @Query("SELECT * FROM accounts WHERE pending=1")
    fun getPendingAccounts(): Flow<AccountEntity?>

    @Query("SELECT * FROM accounts WHERE pending=0 ORDER BY updated_at DESC LIMIT 1")
    suspend fun getLastUpdatedAccount(): AccountEntity?

    @Query("DELETE FROM accounts")
    suspend fun deleteAccounts()

}

//@Transaction
//@Query(
//    "SELECT currencies.* ," +
//            "IFNULL(SUM( CASE WHEN transactions.transaction_type=='INCOME' THEN transactions.amount ELSE 0 END ),0) AS income," +
//            "IFNULL(SUM( CASE WHEN transactions.transaction_type=='EXPENSES' THEN transactions.amount ELSE 0 END),0) AS expenses," +
//            "IFNULL(SUM( CASE WHEN transactions.transaction_type=='DEBTOR' THEN transactions.amount ELSE 0 END),0) AS debtor," +
//            "IFNULL(SUM( CASE WHEN transactions.transaction_type=='CREDITOR' THEN transactions.amount ELSE 0 END ),0) AS creditor " +
//            "FROM currencies " +
//            "JOIN accounts on currencies.currency_id=accounts.currency_id " +
//            "LEFT JOIN transactions on accounts.account_id=transactions.account_id " +
//            "GROUP BY currencies.currency_id"
//)
//fun returnCurrenciesBalance(): Flow<List<CurrencyEntityWithData>>