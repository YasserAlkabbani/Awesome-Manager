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

    @Query("SELECT * FROM currencies WHERE currency_id=:currencyId")
    fun returnCurrencyById(currencyId: String): Flow<CurrencyEntity>

    @Query("SELECT * FROM currencies ORDER BY updated_at DESC LIMIT 1")
    suspend fun returnLastUpdatedCurrencyType(): CurrencyEntity?

    @Transaction
    @Query("SELECT currencies.* ," +
            "IFNULL(SUM( IIF(transactions.payment_transaction=0 AND transaction_types.dead_transaction=1, transactions.amount, 0)),0) AS incoming ," +
            "IFNULL(SUM( IIF(transactions.payment_transaction=1 AND transaction_types.dead_transaction=1, transactions.amount, 0)),0) AS outgoing ," +
            "IFNULL(SUM( IIF(transactions.payment_transaction=0 AND transaction_types.dead_transaction=0, transactions.amount, 0)),0) AS borrow ," +
            "IFNULL(SUM( IIF(transactions.payment_transaction=1 AND transaction_types.dead_transaction=0, transactions.amount, 0)),0) AS lent  " +
            "FROM currencies " +
            "JOIN accounts on currencies.currency_id=accounts.currency_id " +
            "LEFT JOIN transactions on accounts.account_id=transactions.account_id " +
            "LEFT JOIN transaction_types on transactions.transaction_type_id=transaction_types.transaction_type_id " +
            "GROUP BY currencies.currency_id")
    fun returnCurrenciesBalance():Flow<List<CurrencyEntityWithData>>


}