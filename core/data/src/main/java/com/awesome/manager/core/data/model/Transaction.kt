package com.awesome.manager.core.data.model

import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.database.model.TransactionEntityWithData
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.network.model.TransactionNetwork
import kotlinx.datetime.Instant

fun TransactionNetwork.asEntity()=TransactionEntity(
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
    account=accountEntityWithData.asModel(),
    transactionType=transactionTypeEntity.asModel(),
    title=transactionEntity.title,
    subtitle=transactionEntity.subtitle,
    amount=transactionEntity.amount,
    paymentTransaction=transactionEntity.paymentTransaction,
    createdAt=transactionEntity.createdAt.toString(),
    updatedAt=transactionEntity.updatedAt.toString(),
)