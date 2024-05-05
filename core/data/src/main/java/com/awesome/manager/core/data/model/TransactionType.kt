package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.extentions.asDate
import com.awesome.manager.core.common.extentions.asTimestamp
import com.awesome.manager.core.database.model.TransactionTypeEntity
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.network.model.TransactionTypesNetwork
import kotlinx.datetime.Instant

fun TransactionTypesNetwork.asEntity() = TransactionTypeEntity(
    id = id,
    title = title,
    deadTransaction = deadTransaction,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp(),
)

fun TransactionTypeEntity.asModel() = AmTransactionType(
    id = id,
    title = title,
    deadTransaction = deadTransaction,
    updatedAt = updatedAt.asDate(),
    createdAt = createdAt.asDate()
)