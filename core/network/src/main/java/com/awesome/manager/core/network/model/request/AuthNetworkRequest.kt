package com.awesome.manager.core.network.model.request

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Resource("auth/v1")
object Authorization {

    @Resource("token")
    class Login(
        @SerialName("grant_type") val grantType: String = "password",
        val parent: Authorization = Authorization
    )

    @Resource("signup")
    class SignUp(val parent: Authorization = Authorization)

    @Resource("logout")
    class Logout(val parent: Authorization = Authorization)

    @Resource("recover")
    class Recover(val parent: Authorization = Authorization)


    @Resource("token")
    class RefreshToken(
        val parent: Authorization = Authorization,
        @SerialName("grant_type") val grantType: String = "refresh_token"
    )

}

@Serializable
data class LoginRequest(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)

@Serializable
data class SignupRequest(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)