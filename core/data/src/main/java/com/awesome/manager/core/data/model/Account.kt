package com.awesome.manager.core.data.model

import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.AccountEntityWithData
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.network.model.AccountNetworkRequest
import com.awesome.manager.core.network.model.AccountNetworkResponse
import kotlinx.datetime.Instant


fun AccountNetworkResponse.asEntity()=AccountEntity(
    id=id,
    name=name,
    imageUrl=imageUrl,
    currencyId =currencyId,
    defaultTransactionTypeId = defaultTransactionTypeId,
    creatorUserId=creatorUserId,
    pending = false,
    createdAt=Instant.parse(createdAt.orEmpty()).toEpochMilliseconds(),
    updatedAt= Instant.parse(updatedAt.orEmpty()).toEpochMilliseconds(),
)

fun AccountEntityWithData.asModel()=AmAccount(
    id=accountEntity.id,
    creatorUserId = accountEntity.creatorUserId,
    name=accountEntity.name,
    imageUrl=accountEntity.imageUrl,
    currency = defaultCurrencyEntity.asModel(),
    defaultTransactionType = defaultTransactionTypeEntity.asModel(),
    debtor = (1000 .. 5000).random().toDouble() ,
    creditor = (1000 .. 5000).random().toDouble(),
    pending = accountEntity.pending,
    createdAt = accountEntity.createdAt.toString(),
    updatedAt = accountEntity.updatedAt.toString()
)

fun AccountEntity.asNetwork()=AccountNetworkRequest(
    id=id,
    creatorUserId =creatorUserId,
    name=name,
    imageUrl=imageUrl,
    currencyId =currencyId,
    defaultTransactionTypeId = defaultTransactionTypeId,
)