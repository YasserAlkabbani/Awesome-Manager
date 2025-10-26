package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.asTimestamp
import com.awesome.manager.core.common.asStringDateTime
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.database.model.TransactionEntityWithData
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionWithDetails
import com.awesome.manager.core.network.model.request.TransactionNetworkRequest
import com.awesome.manager.core.network.model.response.TransactionNetworkResponse

fun TransactionNetworkResponse.asEntity() = TransactionEntity(
    id = id,
    creatorUserId = creatorUserID,
    accountID = accountID,
    transactionTypeID = transactionTypeID,
    title = title,
    subtitle = subtitle,
    amount = amount,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp(),
    transactionAt = transactionAt.asTimestamp(),
    pending = false,
)

fun TransactionEntityWithData.asModel() = AmTransactionWithDetails(
    transaction = AmTransaction(
        transactionID = transactionEntity.id,
        accountID = transactionEntity.accountID,
        creatorUserID = transactionEntity.creatorUserId,
        transactionTypeID = transactionEntity.transactionTypeID,
        title = transactionEntity.title,
        pending = transactionEntity.pending,
        subtitle = transactionEntity.subtitle,
        amount = transactionEntity.amount,
        createdAt = transactionEntity.createdAt,
        updatedAt = transactionEntity.updatedAt,
        transactionAt = transactionEntity.transactionAt,
//        updatePermission = updatePermission
    ),
    accountName = "accountName",
    currencyCode = "currencyCode",
    currencySymbol = "currencySymbol",
    transactionType = "transactionType",
    isPositive = true
)

fun TransactionEntity.asNetwork() = TransactionNetworkRequest(
    id = id,
    creatorUserID = creatorUserId,
    accountID = accountID,
    transactionTypeID = transactionTypeID,
    title = title,
    subtitle = subtitle,
    amount = amount,
    transactionAt = transactionAt.asStringDateTime()
)

fun AmTransaction.asEntity() = TransactionEntity(
    id = transactionID,
    creatorUserId = creatorUserID,
    accountID = accountID,
    title = title,
    subtitle = subtitle,
    amount = amount,
    transactionTypeID = transactionTypeID,
    transactionAt = transactionAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
    pending = pending,
)