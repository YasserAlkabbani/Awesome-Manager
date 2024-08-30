package com.awesome.manager.core.network.ktor

import android.util.Log
import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import com.awesome.manager.core.network.asResult
import com.awesome.manager.core.network.model.AuthNetwork
import com.awesome.manager.core.network.model.AuthUserNetwork
import com.awesome.manager.core.network.model.LoginRequest
import com.awesome.manager.core.network.model.SignupRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.plugin
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.resources.Resource
import kotlinx.coroutines.flow.first
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject

class KtorAuthNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AuthNetworkDataSource {

    override suspend fun login(email: String, password: String): AuthNetwork =
        httpClient.post(Login()) {
            setBody(LoginRequest(email = email, password = password))
        }.asResult<AuthNetwork>().also { authNetwork ->
            httpClient.plugin(Auth).bearer {
                loadTokens { BearerTokens(authNetwork.accessToken, authNetwork.refreshToken) }
            }
        }

    override suspend fun signUp(email: String, password: String): AuthUserNetwork =
        httpClient.post(SignUp()) {
            setBody(SignupRequest(email = email, password = password))
        }.asResult()

    override suspend fun refreshUser(): AuthNetwork =
        httpClient.get(RefreshUser()).asResult()

    override suspend fun logout() =
        httpClient.post(Logout()).asResult<Unit>()

    suspend fun recoverPassword() =httpClient.get(RefreshUser()).asResult<Unit>()


}

private const val AUTH_URL:String="auth/v1"

@Resource("$AUTH_URL/token")
private class RefreshToken(
    @SerialName("updated_at") val grantType: String = "refresh_token"
)

@Resource("$AUTH_URL/token")
private class Login(
    @SerialName("updated_at") val grantType: String = "password"
)

@Resource("$AUTH_URL/signup")
private class SignUp

@Resource("$AUTH_URL/user")
private class RefreshUser

@Resource("$AUTH_URL/recover")
private class RecoverPassword

@Resource("$AUTH_URL/logout")
private class Logout


