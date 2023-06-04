package com.awesome.manager.core.data.model

import com.awesome.manager.core.model.AmCurrency

fun CurrencyEntity.asModel()=AmCurrency(
    id = id,
    name=name,
    currencySymbol=currencySymbol,
    imageUrl=imageUrl
)