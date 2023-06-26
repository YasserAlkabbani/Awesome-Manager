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
    currencyId= currencyId,
    transactionTypeId= transactionTypeId,
    title=title,
    subtitle=subtitle,
    amount=amount.toString(),
    isPay=isPay,
    createdAt= Instant.parse(createdAt).toEpochMilliseconds(),
    updatedAt= Instant.parse(updatedAt).toEpochMilliseconds()
)

fun TransactionEntityWithData.asDomain()=AmTransaction(
    id=transactionEntity.id,
    creatorUserId=transactionEntity.creatorUserId,
    account=null,
    currency=currencyEntity.asModel(),
    transactionType=transactionTypeEntity.asModel(),
    title=transactionEntity.title,
    subtitle=transactionEntity.subtitle,
    amount=transactionEntity.amount,
    isPay=transactionEntity.isPay,
    createdAt=transactionEntity.createdAt.toString(),
    updatedAt=transactionEntity.updatedAt.toString(),
)