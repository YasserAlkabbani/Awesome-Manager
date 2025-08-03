package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.asTimestamp
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.AccountEntityWithData
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmAccountWithBalance
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.BalanceDetails
import com.awesome.manager.core.network.model.request.AccountNetworkRequest
import com.awesome.manager.core.network.model.response.AccountNetworkResponse

fun AccountEntityWithData.asModel() = AmAccountWithBalance(
    account = AmAccount(
        accountID = accountEntity.id,
        creatorUserID = accountEntity.creatorUserID,
        name = accountEntity.name,
        imageUrl = accountEntity.imageUrl,
        defaultTransactionType = AmTransactionType.returnTransactionType(accountEntity.defaultTransactionType),
        pending = accountEntity.pending,
        alreadyOnNetwork = accountEntity.alreadyOnNetwork,
        createdAt = accountEntity.createdAt,
        updatedAt = accountEntity.updatedAt,
        updatePermission = updatePermission,
        currency = AmCurrency.returnCurrency(accountEntity.currencyCode)
    ),
    balanceDetails = BalanceDetails(
        income = income,
        expenses = expenses,
        debtor = debtor,
        creditor = creditor,
        currency = AmCurrency.returnCurrency(accountEntity.currencyCode)
    ),
)

fun AccountNetworkResponse.asEntity() = AccountEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    currencyCode = currencyCode,
    defaultTransactionType = defaultTransactionType,
    creatorUserID = creatorUserId,
    pending = false,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp(),
    alreadyOnNetwork = true
)

fun AmAccount.asEntity() = AccountEntity(
    id = accountID,
    creatorUserID = creatorUserID,
    name = name,
    imageUrl = imageUrl,
    currencyCode = currency.currencyCode,
    defaultTransactionType = defaultTransactionType.name,
    pending = true,
    alreadyOnNetwork = alreadyOnNetwork,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun AccountEntity.asNetwork() = AccountNetworkRequest(
    id = id,
    creatorUserId = creatorUserID,
    name = name,
    imageUrl = imageUrl,
    currencyCode = currencyCode,
    defaultTransactionType = defaultTransactionType,
)