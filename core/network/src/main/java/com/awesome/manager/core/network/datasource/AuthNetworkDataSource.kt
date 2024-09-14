package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.request.LoginRequest
import com.awesome.manager.core.network.model.request.SignupRequest
import com.awesome.manager.core.network.model.response.AuthNetwork
import com.awesome.manager.core.network.model.response.AuthUserNetwork

interface AuthNetworkDataSource {

    suspend fun login(loginRequest: LoginRequest): AuthNetwork

    suspend fun signUp(signupRequest: SignupRequest): AuthUserNetwork

    suspend fun logout()

    suspend fun recoverPassword()

}