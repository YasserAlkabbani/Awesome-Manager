package com.awesome.manager.core.network.ktor

import android.util.Log
import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import com.awesome.manager.core.network.asResult
import com.awesome.manager.core.network.model.AuthNetwork
import com.awesome.manager.core.network.model.AuthUserNetwork
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
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Resource("auth/v1/")
private class AuthRequest {
    @Resource("token")
    class RefreshToken(
        val parent: AuthRequest = AuthRequest(),
        val grant_type:String="refresh_token"
    )
    @Resource("token")
    class Login(
        val parent: AuthRequest = AuthRequest(),
        val grant_type:String="password"
    )
    @Resource("signup")
    class SignUp(
        val parent: AuthRequest = AuthRequest(),
    )
    @Resource("user")
    class RefreshUser(
        val parent:AuthRequest=AuthRequest()
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
data class LoginRequest(val email: String,val password: String)

@Serializable
data class SignupRequest(val email: String,val password: String)



class KtorAuthNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    AuthNetworkDataSource {

    override suspend fun login(email: String, password: String) : AuthNetwork =
        httpClient.post(AuthRequest.Login()){
            setBody(LoginRequest(email=email,password=password))
        }.asResult<AuthNetwork>().also {authNetwork->
            httpClient.plugin(Auth).bearer {
                loadTokens { BearerTokens(authNetwork.accessToken, authNetwork.refreshToken) }
            }
        }

    override suspend fun signUp(email: String, password: String) : AuthUserNetwork =
        httpClient.post(AuthRequest.SignUp()){
            setBody(SignupRequest(email = email,password=password))
        }.asResult()

    override suspend fun refreshUser():AuthNetwork= httpClient.get(AuthRequest.RefreshUser()).asResult()

//    suspend fun recoverPassword() =httpClient.get(AuthRequest.RefreshUser()).asResult()

    suspend fun logout(){}

    suspend fun refreshToken() {
        val token=httpClient.get("").body<String>()
        httpClient.config {
            install(Auth){
                bearer {
                    refreshTokens {
                        BearerTokens("","")
                    }
                }
            }
        }
    }

}