package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity (
    @ColumnInfo("account_id") @PrimaryKey val id:String,
    @ColumnInfo("name") val name:String,
    @ColumnInfo("image_url") val imageUrl:String,
    @ColumnInfo("currency") val currency: CurrencyEntity,
    @ColumnInfo("created_date") val createdDate:Long,
    @ColumnInfo("should_return_value_by_default") val shouldReturnValueByDefault:Boolean
)