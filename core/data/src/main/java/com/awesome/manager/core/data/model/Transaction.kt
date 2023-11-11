package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.asData
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.database.model.TransactionEntityWithData
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.network.model.TransactionNetworkRequest
import com.awesome.manager.core.network.model.TransactionNetworkResponse
import kotlinx.datetime.Instant

fun TransactionNetworkResponse.asEntity()=TransactionEntity(
    id= id,
    creatorUserId= creatorUserId,
    accountId= accountId,
    transactionTypeId= transactionTypeId,
    title=title,
    subtitle=subtitle,
    amount=amount,
    paymentTransaction =paymentTransaction,
    createdAt= Instant.parse(createdAt).toEpochMilliseconds(),
    updatedAt= Instant.parse(updatedAt).toEpochMilliseconds(),
    pending = false
)

fun TransactionEntityWithData.asDomain()=AmTransaction(
    id=transactionEntity.id,
    creatorUserId=transactionEntity.creatorUserId,
    transactionType=transactionTypeEntity.asModel(),
    title=transactionEntity.title,
    pending = transactionEntity.pending,
    subtitle=transactionEntity.subtitle,
    amount=transactionEntity.amount,
    paymentTransaction=transactionEntity.paymentTransaction,
    createdAt=transactionEntity.createdAt.asData(),
    updatedAt=transactionEntity.updatedAt.asData(),
    accountName = accountEntityWithBasic.accountEntity.name,
    currency = accountEntityWithBasic.currencyEntity.asModel()
)

fun TransactionEntity.asNetwork()=TransactionNetworkRequest(
    id=id,
    creatorUserId=creatorUserId,
    accountId=accountId,
    transactionTypeId=transactionTypeId,
    title=title,
    subtitle=subtitle,
    amount=amount,
    paymentTransaction=paymentTransaction
)