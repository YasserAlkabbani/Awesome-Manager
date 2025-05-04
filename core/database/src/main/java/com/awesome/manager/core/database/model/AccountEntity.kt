package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "accounts")
data class AccountEntity(
    @ColumnInfo("account_id") @PrimaryKey val id: String,
    @ColumnInfo("creator_user_id") val creatorUserID: String,
    @ColumnInfo("currency_id") val currencyId: String,
    @ColumnInfo("default_transaction_type") val defaultTransactionType: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("created_at") val createdAt: Long,
    @ColumnInfo("updated_at") val updatedAt: Long,
    @ColumnInfo("pending") val pending: Boolean,
    @ColumnInfo("already_on_network") val alreadyOnNetwork: Boolean,
)


data class AccountEntityWithData(
    @Embedded val accountEntity: AccountEntity,
    @Relation(parentColumn = "currency_id", entityColumn = "currency_id")
    val currencyEntity: CurrencyEntity,
    @ColumnInfo("income") val income: Double,
    @ColumnInfo("expenses") val expenses: Double,
    @ColumnInfo("debtor") val debtor: Double,
    @ColumnInfo("creditor") val creditor: Double,
    @ColumnInfo("update_permission") val updatePermission: Boolean,
)

data class AccountEntityWithBasic(
    @Embedded val accountEntity: AccountEntity,
    @Relation(parentColumn = "currency_id", entityColumn = "currency_id")
    val currencyEntity: CurrencyEntity
)