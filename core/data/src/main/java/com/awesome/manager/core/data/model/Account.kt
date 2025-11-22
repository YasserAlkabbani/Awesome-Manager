package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.asTimestamp
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.AccountEntityWithData
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmAccountWithDetails
import com.awesome.manager.core.network.model.request.AccountNetworkRequest
import com.awesome.manager.core.network.model.response.AccountNetworkResponse

fun AccountEntityWithData.asModel() = AmAccountWithDetails(
    account = AmAccount(
        accountID = accountEntity.id,
        creatorUserID = accountEntity.creatorUserID,
        name = accountEntity.name,
        imageUrl = accountEntity.imageUrl,
        defaultTransactionTypeID = accountEntity.defaultTransactionTypeID,
        pending = accountEntity.pending,
        createdAt = accountEntity.createdAt,
        updatedAt = accountEntity.updatedAt,
        currencyID = accountEntity.currencyID
    ),
    income = income,
    expenses = expenses,
    debtor = debtor,
    creditor = creditor,
    currencyCode = currencyCode,
    currencySymbol = currencySymbol,
    updatePermission = updatePermission,
)

fun AccountNetworkResponse.asEntity() = AccountEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    currencyID = currencyID,
    defaultTransactionTypeID = defaultTransactionTypeID,
    creatorUserID = creatorUserID,
    pending = false,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp(),
)

fun AccountEntity.asNetwork() = AccountNetworkRequest(
    id = id,
    creatorUserId = creatorUserID,
    name = name,
    imageUrl = imageUrl,
    currencyID = currencyID,
    defaultTransactionTypeID = defaultTransactionTypeID,
)