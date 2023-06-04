package com.awesome.manager.core.data.model

import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.model.AmAccount

fun AccountEntity.asModel()=AmAccount(
    id=id,
    name=name,
    imageUrl=imageUrl,
    createdDate = createdDate,
    currency = currency.asModel(),
    shouldReturnValueByDefault = this.shouldReturnValueByDefault
)