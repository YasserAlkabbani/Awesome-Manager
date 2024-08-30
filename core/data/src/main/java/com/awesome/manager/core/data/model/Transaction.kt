package com.awesome.manager.core.data.model

import com.awesome.manager.core.data.extention.asDate
import com.awesome.manager.core.data.extention.asTimestamp
import com.awesome.manager.core.data.extention.currentTime
import com.awesome.manager.core.data.extention.toStringDateTime
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.database.model.TransactionEntityWithData
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.UpsertTransaction
import com.awesome.manager.core.network.model.TransactionNetworkRequest
import com.awesome.manager.core.network.model.TransactionNetworkResponse

fun TransactionNetworkResponse.asEntity() = TransactionEntity(
    id = id,
    creatorUserId = creatorUserId,
    accountId = accountId,
    transactionType = enumValueOf(transactionType),
    title = title,
    subtitle = subtitle,
    amount = amount,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp(),
    transactionAt = transactionAt.asTimestamp(),
    pending = false
)

fun TransactionEntityWithData.asModel() = AmTransaction(
    id = transactionEntity.id,
    accountId = transactionEntity.accountId,
    creatorUserId = transactionEntity.creatorUserId,
    transactionType = transactionEntity.transactionType.asModel(),
    title = transactionEntity.title,
    pending = transactionEntity.pending,
    subtitle = transactionEntity.subtitle,
    amount = transactionEntity.amount,
    createdAt = transactionEntity.createdAt,
    updatedAt = transactionEntity.updatedAt,
    transactionAt = transactionEntity.transactionAt,
    accountName = accountEntityWithBasic.accountEntity.name,
    currency = accountEntityWithBasic.currencyEntity.asModel(),
    transactionAtDate = transactionEntity.transactionAt.asDate()
)

fun TransactionEntity.asNetwork() = TransactionNetworkRequest(
    id = id,
    creatorUserId = creatorUserId,
    accountId = accountId,
    transactionType = transactionType.name,
    title = title,
    subtitle = subtitle,
    amount = amount,
    transactionAt = transactionAt.toStringDateTime()
)

fun UpsertTransaction.asEntity() = TransactionEntity(
    id = id,
    creatorUserId = creatorUserId,
    accountId = accountId,
    transactionType = transactionType.asEntity(),
    title = title,
    subtitle = subtitle,
    amount = amount,
    createdAt = currentTime(),
    updatedAt = currentTime(),
    transactionAt = transactionAt,
    pending = true,
)