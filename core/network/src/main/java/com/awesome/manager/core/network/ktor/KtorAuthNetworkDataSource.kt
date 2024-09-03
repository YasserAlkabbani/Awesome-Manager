package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import com.awesome.manager.core.network.model.AuthNetwork
import com.awesome.manager.core.network.model.AuthUserNetwork
import com.awesome.manager.core.network.model.LoginRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.plugin
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class KtorAuthNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AuthNetworkDataSource {

    private fun getAuthUrl(path: String = "") = "auth/v1/$path"

    private suspend fun HttpResponse.asResultLoadToken(): AuthNetwork =
        body<AuthNetwork>().apply {
            httpClient.plugin(Auth).bearer {
                loadTokens { BearerTokens(accessToken, refreshToken) }
            }
        }

    override suspend fun login(loginRequest: LoginRequest): AuthNetwork =
        httpClient.post(getAuthUrl("token")) {
            parameter("grant_type", "password")
            setBody(loginRequest)
        }.asResultLoadToken()

    override suspend fun signUp(loginRequest: LoginRequest): AuthUserNetwork =
        httpClient.post(getAuthUrl("signup")) {
            setBody(loginRequest)
        }.body()

    override suspend fun refreshUser(): AuthNetwork =
        httpClient.get(getAuthUrl("user")).body()

    override suspend fun logout(): Unit =
        httpClient.post(getAuthUrl("logout")).body()

    override suspend fun recoverPassword(): Unit =
        httpClient.get(getAuthUrl("recover")).body()

}