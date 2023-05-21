package com.awesome.manager.core.network.ktor

import android.util.Log
import com.awesome.manager.core.network.AuthNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.resources.get
import io.ktor.client.statement.HttpResponse
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Resource("auth/v1/")
class AuthRequest() {
    @Resource("signup")
    class SignUp(val email: String, val password: String)

    @Resource("token?grant_type=password")
    class Login(val email: String, val password: String)
}

@Serializable
data class LoginResponse(val id: Int)

class KtorAuthNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AuthNetworkDataSource {

    override suspend fun login(email: String, password: String) :String =
        httpClient.get(AuthRequest.Login(email, password)).body()

    override suspend fun signUp(email: String, password: String) {
        val response: LoginResponse = httpClient.get(AuthRequest.SignUp(email, password)).body()
    }

    suspend fun refreshToken() {
        httpClient.config {
            install(Auth) {
                bearer {
                    refreshTokens {
                        BearerTokens("def456", "xyz111")
                    }
                }
            }
        }
    }

}