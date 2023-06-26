package com.awesome.manager.core.data.model

import com.awesome.manager.core.database.model.TransactionTypeEntity
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.network.model.TransactionTypesNetwork
import kotlinx.datetime.Instant

fun TransactionTypesNetwork.asEntity() = TransactionTypeEntity(
    id = id,
    title = title,
    isClose = isClose,
    createdAt = Instant.parse(createdAt).toEpochMilliseconds(),
    updatedAt = Instant.parse(updatedAt).toEpochMilliseconds()
)

fun TransactionTypeEntity.asModel() = AmTransactionType(
    id = id,
    title = title,
    isClose = isClose,
    updatedAt = updatedAt.toString(),
    createdAt = createdAt.toString()
)