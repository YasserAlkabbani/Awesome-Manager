package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "currencies")
data class CurrencyEntity(
    @ColumnInfo("currency_id") @PrimaryKey val id:String,
    @ColumnInfo("country_name") val countryName:String,
    @ColumnInfo("image_url") val imageUrl:String,
    @ColumnInfo("currency_code") val currencyCode:String,
    @ColumnInfo("currency_name") val currencyName:String,
    @ColumnInfo("currency_symbol") val currencySymbol:String,
    @ColumnInfo("created_at") val createdAt:Long,
    @ColumnInfo("updated_at") val updatedAt:Long,
)

data class CurrencyEntityWithData(
    @Embedded val currencyEntity: CurrencyEntity,
    @ColumnInfo("incoming") val incoming:Double,
    @ColumnInfo("outgoing") val outgoing:Double,
    @ColumnInfo("borrow") val borrow:Double,
    @ColumnInfo("lent") val lent:Double,
)