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
    creatorUserID = creatorUserID,
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
        creatorUserID = transactionEntity.creatorUserID,
        transactionTypeID = transactionEntity.transactionTypeID,
        title = transactionEntity.title,
        pending = transactionEntity.pending,
        subtitle = transactionEntity.subtitle,
        amount = transactionEntity.amount,
        createdAt = transactionEntity.createdAt,
        updatedAt = transactionEntity.updatedAt,
        transactionAt = transactionEntity.transactionAt,
        updatePermission = updatePermission
    ),
    accountName = accountName,
    currencyCode = currencyCode,
    currencySymbol = currencySymbol,
    transactionType = transactionType,
    isPositive = isPositive,
)

fun TransactionEntity.asNetwork() = TransactionNetworkRequest(
    id = id,
    creatorUserID = creatorUserID,
    accountID = accountID,
    transactionTypeID = transactionTypeID,
    title = title,
    subtitle = subtitle,
    amount = amount,
    transactionAt = transactionAt.asStringDateTime()
)