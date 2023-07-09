package com.awesome.manager.core.data.repository.profile

import com.awesome.manager.core.model.AmUser
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun refreshCurrenttUser()

    suspend fun refreshRelationUser()

    fun returnCurrentUser():Flow<AmUser>

}