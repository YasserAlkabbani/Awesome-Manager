package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @ColumnInfo(name = "currency_id") @PrimaryKey  val id: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "mame") val name: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)