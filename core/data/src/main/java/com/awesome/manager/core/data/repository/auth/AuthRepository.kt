package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.common.ProcessStates
import com.awesome.manager.core.model.AmUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Flow<ProcessStates<Unit>>

    suspend fun signUp(email: String, password: String): Flow<ProcessStates<Unit>>

    suspend fun logout(): Flow<ProcessStates<Unit>>

    fun isLogin(): Flow<Boolean>

    fun currentUser(): Flow<AmUser>

}
