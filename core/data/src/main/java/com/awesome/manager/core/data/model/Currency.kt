package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.extentions.asDate
import com.awesome.manager.core.database.model.CurrencyEntity
import com.awesome.manager.core.database.model.CurrencyEntityWithData
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.CurrencyWithBalance
import com.awesome.manager.core.network.model.CurrencyNetwork
import kotlinx.datetime.Instant

fun CurrencyNetwork.asEntity() = CurrencyEntity(
    id = id,
    countryName = countryName,
    imageUrl = imageUrl,
    currencyCode = currencyCode,
    currencyName = currencyName,
    currencySymbol = currencySymbol,
    createdAt = Instant.parse(createdAt).toEpochMilliseconds(),
    updatedAt = Instant.parse(updatedAt).toEpochMilliseconds()
)

fun CurrencyEntity.asModel() = AmCurrency(
    id = id,
    countryName = countryName,
    imageUrl = imageUrl,
    currencyCode = currencyCode,
    currencyName = currencyName,
    currencySymbol = currencySymbol,
    createdAt = createdAt.asDate(),
    updatedAt = updatedAt.asDate()
)

fun CurrencyEntityWithData.asModel() = CurrencyWithBalance(
    amCurrency = currencyEntity.asModel(),
    incoming = incoming,
    outgoing = outgoing,
    lent = lent,
    borrow = borrow
)
