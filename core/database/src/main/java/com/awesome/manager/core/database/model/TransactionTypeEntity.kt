package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_types")
data class TransactionTypeEntity(
    @ColumnInfo(name = "transaction_type_id") @PrimaryKey val id: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "is_positive") val isPositive: Boolean,
    @ColumnInfo(name = "is_close") val isClose: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)