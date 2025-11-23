package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "transactions")
data class TransactionEntity(
    @ColumnInfo("transaction_id") @PrimaryKey val id: String,
    @ColumnInfo("creator_user_id") val creatorUserID: String,
    @ColumnInfo("account_id") val accountID: String,
    @ColumnInfo("transaction_type_id") val transactionTypeID: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("subtitle") val subtitle: String,
    @ColumnInfo("amount") val amount: Double,
    @ColumnInfo("transaction_at") val transactionAt: Long,
    @ColumnInfo("pending") val pending: Boolean,
    @ColumnInfo("created_at") val createdAt: Long,
    @ColumnInfo("updated_at") val updatedAt: Long,
)

data class TransactionEntityWithData(
    @Embedded val transactionEntity: TransactionEntity,
    @ColumnInfo("update_permission") val updatePermission: Boolean,
    @ColumnInfo("account_name") val accountName: String,
    @ColumnInfo("currency_code") val currencyCode: String,
    @ColumnInfo("currency_symbol") val currencySymbol: String,
    @ColumnInfo("transaction_type") val transactionType: String,
    @ColumnInfo("is_positive") val isPositive: Boolean,
)