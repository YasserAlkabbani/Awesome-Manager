package com.awesome.manager.core.data.model

import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.AccountEntityWithCurrency
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.network.model.AccountNetwork
import kotlinx.datetime.Instant


fun AccountNetwork.asEntity()=AccountEntity(
    id=id,
    name=name,
    imageUrl=imageUrl,
    defaultCurrencyId=defaultCurrencyId,
    defaultTransactionsType=defaultTransactionsType,
    creatorUserId=creatorUserId,
    createdAt=Instant.parse(createdAt).toEpochMilliseconds(),
    updatedAt= Instant.parse(updatedAt).toEpochMilliseconds(),
)

fun AccountEntityWithCurrency.asModel()=AmAccount(
    id=accountEntity.id,
    creatorUserId = accountEntity.creatorUserId,
    name=accountEntity.name,
    imageUrl=accountEntity.imageUrl,
    defaultCurrency = defaultCurrencyEntity.asModel(),
    defaultTransactionsType = accountEntity.defaultTransactionsType,
    createdAt = accountEntity.createdAt,
)

fun AmAccount.asEntity()=AccountEntity(
    id=id,
    creatorUserId =creatorUserId,
    name=name,
    imageUrl=imageUrl,
    defaultCurrencyId = defaultCurrency.id,
    defaultTransactionsType = defaultTransactionsType,
    createdAt = createdAt,
    updatedAt = createdAt,
)