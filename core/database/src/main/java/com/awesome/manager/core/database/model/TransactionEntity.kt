package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "transactions")
data class TransactionEntity(
    @ColumnInfo("transaction_id") @PrimaryKey val id: String,
    @ColumnInfo("creator_user_id") val creatorUserId: String,
    @ColumnInfo("account_id") val accountId: String,
    @ColumnInfo("transaction_type_id") val transactionTypeID: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("subtitle") val subtitle: String,
    @ColumnInfo("amount") val amount: Double,
    @ColumnInfo("created_at") val createdAt: Long,
    @ColumnInfo("updated_at") val updatedAt: Long,
    @ColumnInfo("transaction_at") val transactionAt: Long,
    @ColumnInfo("pending") val pending: Boolean,
    @ColumnInfo("already_on_network") val alreadyOnNetwork: Boolean,
)

data class TransactionEntityWithData(
    @Embedded val transactionEntity: TransactionEntity,
    @Relation(
        parentColumn = "account_id", entityColumn = "account_id",
        entity = AccountEntity::class
    )
    val accountEntity: AccountEntity,
    @ColumnInfo("update_permission") val updatePermission: Boolean,
)