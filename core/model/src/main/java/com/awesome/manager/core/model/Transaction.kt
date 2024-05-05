package com.awesome.manager.core.model

data class AmTransaction(
    val id: String,
    val accountId: String,
    val creatorUserId: String,
    val transactionType: AmTransactionType,
    val title: String,
    val subtitle: String,
    val amount: Double,
    val pending: Boolean,
    val paymentTransaction: Boolean,
    val accountName: String,
    val currency: AmCurrency,
    val createdAt: String,
    val updatedAt: String,
    val transactionAt:String,
)

data class UpsertTransaction(
    val id: String,
    val accountId: String,
    val creatorUserId: String,
    val title: String,
    val subtitle: String,
    val amount: Double,
    val transactionTypeId: String,
    val paymentTransaction: Boolean,
    val transactionAt:String,
)