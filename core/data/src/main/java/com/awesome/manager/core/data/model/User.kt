package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.extentions.asDate
import com.awesome.manager.core.common.extentions.asTimestamp
import com.awesome.manager.core.database.model.UserEntity
import com.awesome.manager.core.model.AmUser
import com.awesome.manager.core.network.model.UserNetwork
import kotlinx.datetime.Instant

fun UserNetwork.asEntity() = UserEntity(
    id=id,
    email=email,
    name = name,
    imageUrl = imageUrl,
    createdAt = createdAt.asTimestamp(),
    updatedAt = updatedAt.asTimestamp()
)

fun UserEntity.asDomain() = AmUser(
    id=id,
    email=email,
    name = name,
    imageUrl = imageUrl,
    createdAt = createdAt.asDate(),
    updatedAt = updatedAt.asDate()
)