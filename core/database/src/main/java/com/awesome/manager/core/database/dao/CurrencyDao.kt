package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.awesome.manager.core.database.model.CurrencyEntity
import com.awesome.manager.core.database.model.TransactionTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    fun upsertCurrency(currencyEntity: List<CurrencyEntity>)

    @Query("SELECT * FROM currencies")
    fun returnCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies WHERE currency_id=:currencyId")
    fun returnCurrencyById(currencyId: String): Flow<CurrencyEntity>

    @Query("SELECT * FROM currencies ORDER BY updated_at DESC LIMIT 1")
    fun returnLastUpdatedCurrencyType(): CurrencyEntity?

}