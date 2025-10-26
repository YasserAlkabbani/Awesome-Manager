package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.asTimestamp
import com.awesome.manager.core.database.model.TransactionTypeEntity
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.network.model.response.TransactionTypeNetworkResponse

fun TransactionTypeNetworkResponse.asEntity() = TransactionTypeEntity(
    id = id,
    type = type,
    isPositive = isPositive,
    isClose = isClose,
    createdAt = createdAt.asTimestamp(),
)

fun TransactionTypeEntity.asModel() = AmTransactionType(
    id = id,
    type = type,
    isPositive = isPositive,
    isClose = isClose,
    createdAt = createdAt,
)