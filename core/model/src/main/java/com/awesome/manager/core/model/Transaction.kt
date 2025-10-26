package com.awesome.manager.core.model

import com.awesome.manager.core.common.asDate
import java.text.NumberFormat
import java.util.Locale

data class AmTransaction(
    val transactionID: String,
    val accountID: String,
    val creatorUserID: String,
    val transactionTypeID: String,
    val title: String,
    val subtitle: String,
    val amount: Double,
    val pending: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val transactionAt: Long,
) {
    val formattedAmount: String = NumberFormat.getNumberInstance(Locale.US).format(amount)
    val transactionAtDate: String = transactionAt.asDate()
}

data class AmTransactionWithDetails(
    val transaction: AmTransaction,
    val accountName: String,
    val currencyCode: String,
    val currencySymbol: String,
    val isPositive: Boolean,
    val transactionType: String,
)