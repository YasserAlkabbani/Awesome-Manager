package com.awesome.manager.core.model

data class AmAccount (
    val id:String,
    val creatorUserId:String,
    val name:String,
    val imageUrl:String,
    val defaultCurrency: AmCurrency,
    val defaultTransactionsType:AmTransactionType,
    val createdAt:String,
    val updatedAt:String,
)