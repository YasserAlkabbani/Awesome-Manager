package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.awesome.manager.core.database.model.CurrencyEntity
import com.awesome.manager.core.database.model.CurrencyEntityWithData
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    suspend fun upsertCurrency(currencyEntity: List<CurrencyEntity>)

    @Query("SELECT * FROM currencies")
    fun returnCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies ORDER BY updated_at DESC LIMIT 1")
    suspend fun returnLastUpdatedCurrencyType(): CurrencyEntity?

    @Transaction
    @Query(
        "SELECT currencies.* ," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type=='INCOME' THEN transactions.amount ELSE 0 END ),0) AS income," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type=='EXPENSES' THEN transactions.amount ELSE 0 END),0) AS expenses," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type=='DEBTOR' THEN transactions.amount ELSE 0 END),0) AS debtor," +
                "IFNULL(SUM( CASE WHEN transactions.transaction_type=='CREDITOR' THEN transactions.amount ELSE 0 END ),0) AS creditor " +
                "FROM currencies " +
                "JOIN accounts on currencies.currency_id=accounts.currency_id " +
                "LEFT JOIN transactions on accounts.account_id=transactions.account_id " +
                "GROUP BY currencies.currency_id"
    )
    fun returnCurrenciesBalance(): Flow<List<CurrencyEntityWithData>>

}