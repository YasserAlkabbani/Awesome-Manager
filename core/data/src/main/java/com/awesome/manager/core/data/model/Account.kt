package com.awesome.manager.core.data.model

import com.awesome.manager.core.data.extention.asTimestamp
import com.awesome.manager.core.data.extention.currentTime
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.AccountEntityWithData
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.BalanceDetails
import com.awesome.manager.core.model.UpsertAccount
import com.awesome.manager.core.network.model.AccountNetworkRequest
import com.awesome.manager.core.network.model.AccountNetworkResponse


fun AccountNetworkResponse.asEntity() = AccountEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    currencyId = currencyId,
    defaultTransactionType = enumValueOf(defaultTransactionType),
    creatorUserId = creatorUserId,
    pending = false,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp(),
)

fun AccountEntityWithData.asModel() = AmAccount(
    id = accountEntity.id,
    creatorUserId = accountEntity.creatorUserId,
    name = accountEntity.name,
    imageUrl = accountEntity.imageUrl,
    defaultTransactionType = accountEntity.defaultTransactionType.asModel(),
    balanceDetails = BalanceDetails(
        income = income, expenses = expenses, debtor = debtor, creditor = creditor,
        currency = currencyEntity.asModel(),
    ),
    pending = accountEntity.pending,
    createdAt = accountEntity.createdAt,
    updatedAt = accountEntity.updatedAt
)

fun AccountEntity.asNetwork() = AccountNetworkRequest(
    id = id,
    creatorUserId = creatorUserId,
    name = name,
    imageUrl = imageUrl,
    currencyId = currencyId,
    defaultTransactionType = defaultTransactionType.name,
)

fun UpsertAccount.asEntity() = AccountEntity(
    id = id,
    creatorUserId = creatorUserId,
    name = name,
    imageUrl = imageUrl,
    currencyId = currencyId,
    defaultTransactionType = defaultTransactionType.asEntity(),
    pending = true,
    createdAt = currentTime(),
    updatedAt = currentTime(),
)