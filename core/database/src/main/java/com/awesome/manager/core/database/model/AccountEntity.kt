package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "accounts")
data class AccountEntity (
    @ColumnInfo("account_id") @PrimaryKey val id:String,
    @ColumnInfo("name") val name:String,
    @ColumnInfo("image_url") val imageUrl:String,
    @ColumnInfo("creator_user_id") val creatorUserId:String,
    @ColumnInfo("default_currency_id") val defaultCurrencyId:String,
    @ColumnInfo("default_transactions_type") val defaultTransactionsType:String,
    @ColumnInfo("created_at") val createdAt:Long,
    @ColumnInfo("updated_at") val updatedAt:Long,
)


data class AccountEntityWithCurrency (
    @Embedded val accountEntity:AccountEntity,
    @Relation (parentColumn = "default_currency_id", entityColumn ="currency_id") val defaultCurrencyEntity:CurrencyEntity,
)