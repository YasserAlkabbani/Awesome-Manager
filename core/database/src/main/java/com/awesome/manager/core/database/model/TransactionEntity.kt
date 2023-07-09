package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "transactions")
data class TransactionEntity(
    @ColumnInfo("transaction_id") @PrimaryKey val id: String,
    @ColumnInfo("creator_user_id")val creatorUserId: String,
    @ColumnInfo("account_id")val accountId: String,
    @ColumnInfo("transaction_type_id")val transactionTypeId: String,
    @ColumnInfo("title") val title:String,
    @ColumnInfo("subtitle") val subtitle:String,
    @ColumnInfo("amount") val amount:Double,
    @ColumnInfo("is_pay") val isPay:Boolean,
    @ColumnInfo("created_at") val createdAt:Long,
    @ColumnInfo("updated_at") val updatedAt:Long,
)

data class TransactionEntityWithData(
    @Embedded val transactionEntity:TransactionEntity,
    @Relation (entity = AccountEntity::class,parentColumn = "account_id", entityColumn ="account_id") val accountEntityWithData: AccountEntityWithData,
    @Relation (parentColumn = "transaction_type_id", entityColumn ="transaction_type_id") val transactionTypeEntity: TransactionTypeEntity,
)