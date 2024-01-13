package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.network.model.AuthNetwork
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Flow<AmResult<Boolean>>

    suspend fun signUp(email: String, password: String): Flow<AmResult<Boolean>>

    suspend fun logout(): Flow<AmResult<Unit>>

    suspend fun refreshUserInfo()

    suspend fun updateToken(authNetwork: AuthNetwork)

    fun isLogin(): Flow<Boolean>

    fun currentUserId(): Flow<String>

    fun currentUserEmail(): Flow<String>

}
