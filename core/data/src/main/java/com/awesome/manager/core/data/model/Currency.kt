package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.asTimestamp
import com.awesome.manager.core.database.model.CurrencyEntity
import com.awesome.manager.core.database.model.CurrencyEntityWithData
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.BalanceDetails
import com.awesome.manager.core.network.model.response.CurrencyNetwork

fun CurrencyNetwork.asEntity() = CurrencyEntity(
    id = id,
    countryName = countryName,
    imageUrl = imageUrl,
    currencyCode = currencyCode,
    currencyName = currencyName,
    currencySymbol = currencySymbol,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp()
)

fun CurrencyEntity.asModel() = AmCurrency(
    id = id,
    countryName = countryName,
    imageUrl = imageUrl,
    currencyCode = currencyCode,
    currencyName = currencyName,
    currencySymbol = currencySymbol,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun CurrencyEntityWithData.asModel() = BalanceDetails(
    currency = currencyEntity.asModel(),
    income = income, expenses = expenses, debtor = debtor, creditor = creditor,
)
