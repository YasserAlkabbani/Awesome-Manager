package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "transactions")
data class TransactionEntity(
    @ColumnInfo("transaction_id") val id: String,
    @ColumnInfo("account_id") val accountId:String,
    @ColumnInfo("creator_id") val creatorId:String,
    @ColumnInfo("title") val title:String,
    @ColumnInfo("subTitle") val subTitle:String,
    @ColumnInfo("created_date") val createdDate:Long,
    @ColumnInfo("incoming_amount") val incomingAmount:Double,
    @ColumnInfo("outgoing_amount") val outgoingAmount:Double,
    @ColumnInfo("should_return") val shouldReturn:Boolean
)