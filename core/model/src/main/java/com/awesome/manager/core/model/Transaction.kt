package com.awesome.manager.core.model

data class AmTransaction(
    val id: String,
    val creatorUserId: String,
    val transactionType: AmTransactionType,
    val title:String,
    val subtitle:String,
    val amount:Double,
    val paymentTransaction:Boolean,
    val createdAt:String,
    val updatedAt:String
)