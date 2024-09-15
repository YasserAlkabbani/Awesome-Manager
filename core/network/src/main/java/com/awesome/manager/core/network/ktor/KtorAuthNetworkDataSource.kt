package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import com.awesome.manager.core.network.model.request.Authorization
import com.awesome.manager.core.network.model.request.LoginRequest
import com.awesome.manager.core.network.model.request.SignupRequest
import com.awesome.manager.core.network.model.response.AuthNetwork
import com.awesome.manager.core.network.model.response.AuthUserNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.plugin
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class KtorAuthNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AuthNetworkDataSource {

    private fun AuthNetwork.loadToken(): AuthNetwork = apply {
        httpClient.plugin(Auth).bearer {
            loadTokens { BearerTokens(accessToken, refreshToken) }
        }
    }

    override suspend fun login(loginRequest: LoginRequest): AuthNetwork =
        httpClient.post(Authorization.Login()) {
            setBody(loginRequest)
        }.body<AuthNetwork>().loadToken()

    override suspend fun signUp(signupRequest: SignupRequest): AuthUserNetwork =
        httpClient.post(Authorization.SignUp()) {
            setBody(signupRequest)
        }.body()

    override suspend fun logout(): Unit =
        httpClient.post(Authorization.Logout()).body()

    override suspend fun recoverPassword(): Unit =
        httpClient.get(Authorization.Recover()).body()

}