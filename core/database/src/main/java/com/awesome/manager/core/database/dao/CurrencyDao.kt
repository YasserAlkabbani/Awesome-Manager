package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.awesome.manager.core.database.model.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    fun upsertCurrency(currencyEntity: CurrencyEntity)

    @Upsert
    fun upsertCurrency(currencyEntity: List<CurrencyEntity>)

    @Query("SELECT * FROM currencies")
    fun returnCurrencies():Flow<List<CurrencyEntity>>

}