package com.awesome.manager.core.model

data class AmTransactionType(
    val id: String,
    val title: String,
    val deadTransaction: Boolean,
    val createdAt: String,
    val updatedAt: String
)