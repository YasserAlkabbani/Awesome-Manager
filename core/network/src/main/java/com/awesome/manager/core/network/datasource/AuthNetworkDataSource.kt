package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.AuthNetwork

interface AuthNetworkDataSource {

    suspend fun login(email:String,password: String): AuthNetwork

    suspend fun signUp(email: String,password:String): String

    suspend fun refreshUser(): AuthNetwork

}