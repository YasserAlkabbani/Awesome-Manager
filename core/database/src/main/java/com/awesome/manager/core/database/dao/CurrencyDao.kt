package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    fun upsertCurrency(currencyEntity: CurrencyEntity)

    @Query("SELECT * FROM currencies")
    fun returnCurrencies():Flow<List<CurrencyEntity>>

}