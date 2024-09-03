package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.AuthNetwork
import com.awesome.manager.core.network.model.AuthUserNetwork
import com.awesome.manager.core.network.model.LoginRequest

interface AuthNetworkDataSource {

    suspend fun login(loginRequest: LoginRequest): AuthNetwork

    suspend fun signUp(loginRequest: LoginRequest): AuthUserNetwork

    suspend fun refreshUser(): AuthNetwork

    suspend fun logout()

    suspend fun recoverPassword()

}