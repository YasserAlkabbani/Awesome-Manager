package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.model.AmUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Flow<AmState<Unit>>

    suspend fun signUp(email: String, password: String): Flow<AmState<Unit>>

    suspend fun logout(): Flow<AmState<Unit>>

    fun isLogin(): Flow<Boolean>

    fun currentUser(): Flow<AmUser>

}
