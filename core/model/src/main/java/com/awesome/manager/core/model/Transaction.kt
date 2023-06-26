package com.awesome.manager.core.model

data class AmTransaction(
    val id: String,
    val creatorUserId: String,
    val account: AmAccount?,
    val currency: AmCurrency,
    val transactionType: AmTransactionType,
    val title:String,
    val subtitle:String,
    val amount:String,
    val isPay:Boolean,
    val createdAt:String,
    val updatedAt:String
)