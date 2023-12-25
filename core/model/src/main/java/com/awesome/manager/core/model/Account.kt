package com.awesome.manager.core.model

data class AmAccount(
    val id: String,
    val creatorUserId: String,
    val name: String,
    val imageUrl: String,
    val currency: AmCurrency,
    val defaultTransactionType: AmTransactionType,
    val creditor: Double,
    val debtor: Double,
    val pending: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

data class UpsertAccount(
    val id: String,
    val creatorUserId: String,
    val name: String,
    val imageUrl: String,
    val currencyId: String,
    val defaultTransactionTypeId: String
)