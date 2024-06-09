package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.awesome.manager.core.database.model.CurrencyEntity
import com.awesome.manager.core.database.model.CurrencyEntityWithData
import com.awesome.manager.core.database.model.TransactionTypeEntity
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
    @Query(
        "SELECT currencies.* ," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:income, transactions.amount, 0)),0) AS income," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:expenses, transactions.amount, 0)),0) AS expenses," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:debtor, transactions.amount, 0)),0) AS debtor," +
                "IFNULL(SUM( IIF(transactions.transaction_type=:creditor, transactions.amount, 0)),0) AS creditor " +
                "FROM currencies " +
                "JOIN accounts on currencies.currency_id=accounts.currency_id " +
                "LEFT JOIN transactions on accounts.account_id=transactions.account_id " +
                "GROUP BY currencies.currency_id"
    )
    fun returnCurrenciesBalance(
        income: TransactionTypeEntity = TransactionTypeEntity.INCOME,
        expenses: TransactionTypeEntity = TransactionTypeEntity.EXPENSES,
        debtor: TransactionTypeEntity = TransactionTypeEntity.DEBTOR,
        creditor: TransactionTypeEntity = TransactionTypeEntity.CREDITOR,
    ): Flow<List<CurrencyEntityWithData>>

}