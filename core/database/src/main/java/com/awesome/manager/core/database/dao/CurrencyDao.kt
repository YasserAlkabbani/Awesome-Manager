package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.awesome.manager.core.database.model.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    suspend fun upsertCurrencies(currencyEntity: List<CurrencyEntity>)

    @Query("SELECT * FROM currencies")
    fun returnCurrencies(): Flow<List<CurrencyEntity>>

}