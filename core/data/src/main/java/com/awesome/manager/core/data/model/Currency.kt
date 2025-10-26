package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.asTimestamp
import com.awesome.manager.core.database.model.CurrencyEntity
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.network.model.response.CurrencyNetwork

fun CurrencyNetwork.asEntity() = CurrencyEntity(
    id = id,
    code = code,
    name = name,
    symbol = symbol,
    createdAt = createdAt.asTimestamp()
)

fun CurrencyEntity.asModel() = AmCurrency(
    id = id,
    code = code,
    name = name,
    symbol = symbol
)