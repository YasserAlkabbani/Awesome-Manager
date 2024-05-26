package com.awesome.manager.core.model

data class AmAccount(
    val id: String,
    val creatorUserId: String,
    val name: String,
    val imageUrl: String,
    val defaultTransactionType: AmTransactionType,
    val balanceDetails: BalanceDetails,
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
    val defaultTransactionType: AmTransactionType
)