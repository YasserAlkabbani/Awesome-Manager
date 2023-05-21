package com.awesome.manager.core.network

interface AuthNetworkDataSource {

    suspend fun login(email:String,password: String): String

    suspend fun signUp(email: String,password:String)

}