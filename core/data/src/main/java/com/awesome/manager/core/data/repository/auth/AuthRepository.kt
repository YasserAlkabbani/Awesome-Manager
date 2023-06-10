package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.common.AmResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email:String,password:String): Flow<AmResult<String>>

    suspend fun signUp(email: String,password: String): Flow<AmResult<String>>

    suspend fun refreshUserInfo():Flow<AmResult<Any>>

    fun isLogin(): Flow<Boolean>
}
