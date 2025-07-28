package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.asDate
import com.awesome.manager.core.common.asTimestamp
import com.awesome.manager.core.common.currentTime
import com.awesome.manager.core.common.asStringDateTime
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.database.model.TransactionEntityWithData
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import com.awesome.manager.core.network.model.request.TransactionNetworkRequest
import com.awesome.manager.core.network.model.response.TransactionNetworkResponse

fun TransactionNetworkResponse.asEntity() = TransactionEntity(
    id = id,
    creatorUserId = creatorUserId,
    accountId = accountId,
    transactionTypeID = transactionType,
    title = title,
    subtitle = subtitle,
    amount = amount,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp(),
    transactionAt = transactionAt.asTimestamp(),
    pending = false,
    alreadyOnNetwork = true
)

fun TransactionEntityWithData.asModel() = AmTransaction(
    transactionID = transactionEntity.id,
    accountID = transactionEntity.accountId,
    creatorUserID = transactionEntity.creatorUserId,
    transactionType = AmTransactionType.returnTransactionType(transactionEntity.transactionTypeID),
    title = transactionEntity.title,
    pending = transactionEntity.pending,
    alreadyOnNetwork = transactionEntity.alreadyOnNetwork,
    subtitle = transactionEntity.subtitle,
    amount = transactionEntity.amount,
    createdAt = transactionEntity.createdAt,
    updatedAt = transactionEntity.updatedAt,
    transactionAt = transactionEntity.transactionAt,
    accountName = accountEntity.name,
    currency = AmCurrency.returnCurrency(accountEntity.currencyID),
    updatePermission = updatePermission,
    transactionAtDate = transactionEntity.transactionAt.asDate()
)

fun TransactionEntity.asNetwork() = TransactionNetworkRequest(
    id = id,
    creatorUserId = creatorUserId,
    accountId = accountId,
    transactionType = transactionTypeID,
    title = title,
    subtitle = subtitle,
    amount = amount,
    transactionAt = transactionAt.asStringDateTime()
)

fun UpsertTransaction.asEntity() = TransactionEntity(
    id = transactionID,
    creatorUserId = creatorUserId,
    accountId = accountId,
    transactionTypeID = transactionType,
    title = title,
    subtitle = subtitle,
    amount = amount,
    createdAt = currentTime(),
    updatedAt = currentTime(),
    transactionAt = transactionAt,
    pending = true,
    alreadyOnNetwork = alreadyOnNetwork
)