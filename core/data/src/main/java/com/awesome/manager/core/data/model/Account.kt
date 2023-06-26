package com.awesome.manager.core.data.model

import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.AccountEntityWithData
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.network.model.AccountNetwork
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant


fun AccountNetwork.asEntity()=AccountEntity(
    id=id.toString(),
    name=name,
    imageUrl=imageUrl,
    defaultCurrencyId=defaultCurrencyId.toString(),
    defaultTransactionsTypeId = defaultTransactionTypeId.toString(),
    creatorUserId=creatorUserId.toString(),
    createdAt=Instant.parse(createdAt).toEpochMilliseconds(),
    updatedAt= Instant.parse(updatedAt).toEpochMilliseconds(),
)

fun AccountEntityWithData.asModel()=AmAccount(
    id=accountEntity.id,
    creatorUserId = accountEntity.creatorUserId,
    name=accountEntity.name,
    imageUrl=accountEntity.imageUrl,
    defaultCurrency = defaultCurrencyEntity.asModel(),
    defaultTransactionsType = defaultTransactionTypeEntity.asModel(),
    createdAt = accountEntity.createdAt.toString(),
    updatedAt = accountEntity.updatedAt.toString()
)

fun AmAccount.asEntity()=AccountEntity(
    id=id,
    creatorUserId =creatorUserId,
    name=name,
    imageUrl=imageUrl,
    defaultCurrencyId = defaultCurrency.id,
    defaultTransactionsTypeId = defaultTransactionsType.id,
    createdAt = Clock.System.now().toEpochMilliseconds(),
    updatedAt = Clock.System.now().toEpochMilliseconds(),
)