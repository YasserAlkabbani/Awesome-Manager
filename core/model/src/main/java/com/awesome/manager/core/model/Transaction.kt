package com.awesome.manager.core.model

import java.text.NumberFormat
import java.util.Locale

data class AmTransaction(
    val id: String,
    val accountId: String,
    val creatorUserId: String,
    val transactionType: AmTransactionType,
    val title: String,
    val subtitle: String,
    val amount: Double,
    val pending: Boolean,
    val accountName: String,
    val currency: AmCurrency,
    val createdAt: Long,
    val updatedAt: Long,
    val transactionAt: Long,
    val transactionAtDate: String
){
    val formattedAmount:String= NumberFormat.getNumberInstance(Locale.US).format(amount)
}

data class UpsertTransaction(
    val id: String,
    val accountId: String,
    val creatorUserId: String,
    val title: String,
    val subtitle: String,
    val amount: Double,
    val transactionType: AmTransactionType,
    val transactionAt: Long,
)