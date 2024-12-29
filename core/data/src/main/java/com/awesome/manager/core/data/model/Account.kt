package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.asTimestamp
import com.awesome.manager.core.common.currentTime
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.AccountEntityWithData
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.BalanceDetails
import com.awesome.manager.core.model.UpsertAccount
import com.awesome.manager.core.network.model.request.AccountNetworkRequest
import com.awesome.manager.core.network.model.response.AccountNetworkResponse


fun AccountNetworkResponse.asEntity() = AccountEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    currencyId = currencyId,
    defaultTransactionType = defaultTransactionType,
    creatorUserId = creatorUserId,
    pending = false,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp(),
)

fun AccountEntityWithData.asModel() = AmAccount(
    id = accountEntity.id,
    creatorUserID = accountEntity.creatorUserId,
    name = accountEntity.name,
    imageUrl = accountEntity.imageUrl,
    defaultTransactionType = enumValueOf(accountEntity.defaultTransactionType),
    balanceDetails = BalanceDetails(
        income = income, expenses = expenses, debtor = debtor, creditor = creditor,
        currency = currencyEntity.asModel(),
    ),
    pending = accountEntity.pending,
    createdAt = accountEntity.createdAt,
    updatedAt = accountEntity.updatedAt,
    updatePermission = updatePermission
)

fun AccountEntity.asNetwork() = AccountNetworkRequest(
    id = id,
    creatorUserId = creatorUserId,
    name = name,
    imageUrl = imageUrl,
    currencyId = currencyId,
    defaultTransactionType = defaultTransactionType,
)

fun UpsertAccount.asEntity() = AccountEntity(
    id = id,
    creatorUserId = creatorUserId,
    name = name,
    imageUrl = imageUrl,
    currencyId = currencyId,
    defaultTransactionType = defaultTransactionTypeID,
    pending = true,
    createdAt = currentTime(),
    updatedAt = currentTime(),
)