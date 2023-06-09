package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import com.awesome.manager.core.network.model.UserNetwork
import com.awesome.manager.core.network.asResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Resource("auth/v1/")
private class AuthRequest {
    @Resource("token")
    class Login(
        val parent: AuthRequest = AuthRequest(),
        val grant_type:String="password"
    )
    @Resource("signup")
    class SignUp(
        val parent: AuthRequest = AuthRequest(),
    )
    @Resource("recover")
    class RecoverPassword(
        val parent: AuthRequest = AuthRequest()
    )
    @Resource("logout")
    class Logout(
        val parent: AuthRequest = AuthRequest()
    )
}

@Serializable
data class LoginResponse(
    @SerialName ("access_token") val accessToken:String?,
    @SerialName ("refresh_token") val refreshToken:String?,
    val user:UserNetwork,
)

@Serializable
data class LoginRequest(val email: String,val password: String)



class KtorAuthNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AuthNetworkDataSource {

    override suspend fun login(email: String, password: String) :LoginResponse =
        httpClient.post(AuthRequest.Login()){
            setBody(LoginRequest(email,password))
        }.asResult()


    override suspend fun signUp(email: String, password: String) =
        httpClient.post(AuthRequest.SignUp()).body<String>()

    suspend fun recoverPassword(){}

    suspend fun logout(){}

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