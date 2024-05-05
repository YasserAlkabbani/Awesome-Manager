package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.extentions.asDate
import com.awesome.manager.core.common.extentions.asTimestamp
import com.awesome.manager.core.common.extentions.currentTime
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.database.model.TransactionEntityWithData
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.UpsertTransaction
import com.awesome.manager.core.network.model.TransactionNetworkRequest
import com.awesome.manager.core.network.model.TransactionNetworkResponse
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

fun TransactionNetworkResponse.asEntity() = TransactionEntity(
    id = id,
    creatorUserId = creatorUserId,
    accountId = accountId,
    transactionTypeId = transactionTypeId,
    title = title,
    subtitle = subtitle,
    amount = amount,
    paymentTransaction = paymentTransaction,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp(),
    transactionAt=updatedAt.asTimestamp(),
    pending = false
)

fun TransactionEntityWithData.asDomain() = AmTransaction(
    id = transactionEntity.id,
    accountId = transactionEntity.accountId,
    creatorUserId = transactionEntity.creatorUserId,
    transactionType = transactionTypeEntity.asModel(),
    title = transactionEntity.title,
    pending = transactionEntity.pending,
    subtitle = transactionEntity.subtitle,
    amount = transactionEntity.amount,
    paymentTransaction = transactionEntity.paymentTransaction,
    createdAt = transactionEntity.createdAt.asDate(),
    updatedAt = transactionEntity.updatedAt.asDate(),
    transactionAt = transactionEntity.updatedAt.asDate(),
    accountName = accountEntityWithBasic.accountEntity.name,
    currency = accountEntityWithBasic.currencyEntity.asModel(),
)

fun TransactionEntity.asNetwork() = TransactionNetworkRequest(
    id = id,
    creatorUserId = creatorUserId,
    accountId = accountId,
    transactionTypeId = transactionTypeId,
    title = title,
    subtitle = subtitle,
    amount = amount,
    paymentTransaction = paymentTransaction,
    transactionAt = Instant.fromEpochMilliseconds(transactionAt).toString()
)

fun UpsertTransaction.asEntity() = TransactionEntity(
    id = id,
    creatorUserId = creatorUserId,
    accountId = accountId,
    transactionTypeId = transactionTypeId,
    title = title,
    subtitle = subtitle,
    amount = amount,
    paymentTransaction = paymentTransaction,
    createdAt = currentTime(),
    updatedAt = currentTime(),
    transactionAt = transactionAt.asTimestamp(),
    pending = true,
)