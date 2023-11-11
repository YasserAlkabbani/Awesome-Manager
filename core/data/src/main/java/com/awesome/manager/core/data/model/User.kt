package com.awesome.manager.core.data.model

import com.awesome.manager.core.common.asData
import com.awesome.manager.core.database.model.UserEntity
import com.awesome.manager.core.model.AmUser
import com.awesome.manager.core.network.model.UserNetwork
import kotlinx.datetime.Instant

fun UserNetwork.asEntity() = UserEntity(
    id=id,
    email=email,
    name = name,
    imageUrl = imageUrl,
    createdAt = Instant.parse(createdAt).toEpochMilliseconds(),
    updatedAt = Instant.parse(updatedAt).toEpochMilliseconds()
)

fun UserEntity.asDomain() = AmUser(
    id=id,
    email=email,
    name = name,
    imageUrl = imageUrl,
    createdAt = createdAt.asData(),
    updatedAt = updatedAt.asData()
)