package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.AuthNetwork
import com.awesome.manager.core.network.model.AuthUserNetwork

interface AuthNetworkDataSource {

    suspend fun login(email: String, password: String): AuthNetwork

    suspend fun signUp(email: String, password: String): AuthUserNetwork

    suspend fun refreshUser(): AuthNetwork

    suspend fun logout()

}