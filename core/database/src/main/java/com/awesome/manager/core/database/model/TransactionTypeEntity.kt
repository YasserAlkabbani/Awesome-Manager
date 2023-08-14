package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_types")
data class TransactionTypeEntity(
    @ColumnInfo("transaction_type_id") @PrimaryKey val id: String,
    @ColumnInfo("title") val title:String,
    @ColumnInfo("dead_transaction") val deadTransaction:Boolean,
    @ColumnInfo("default_payment_transaction") val defaultPaymentTransaction:Boolean,
    @ColumnInfo("switch_payment_transaction") val switchPaymentTransaction:Boolean,
    @ColumnInfo("created_at") val createdAt:Long,
    @ColumnInfo("updated_at") val updatedAt:Long,
)