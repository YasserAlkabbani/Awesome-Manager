package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.network.model.response.AuthNetwork
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Flow<AmUIState<Unit>>

    suspend fun signUp(email: String, password: String): Flow<AmUIState<Unit>>

    suspend fun logout(): Flow<AmUIState<Unit>>

    suspend fun updateToken(authNetwork: AuthNetwork)

    fun isLogin(): Flow<Boolean>

    fun currentUserId(): Flow<String>

    fun currentUserEmail(): Flow<String>

}
