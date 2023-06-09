package com.awesome.manager.core.data.model

import com.awesome.manager.core.database.model.CurrencyEntity
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.network.model.CurrencyNetwork

fun CurrencyEntity.asModel()=AmCurrency(
    id=id,
    countryName=countryName,
    imageUrl=imageUrl,
    currencyCode=currencyCode,
    currencyName=currencyName,
    currencySymbol=currencySymbol,
    createdAt=createdAt,
    updatedAt=updatedAt
)

fun CurrencyNetwork.asEntity()=CurrencyEntity(
    id=id,
    countryName=countryName,
    imageUrl=imageUrl,
    currencyCode=currencyCode,
    currencyName=currencyName,
    currencySymbol=currencySymbol,
    createdAt=createdAt,
    updatedAt=updatedAt
)