package com.awesome.manager.core.network.di

import android.content.Context
import com.awesome.manager.core.datastore.AuthPreferencesDataStore
import com.awesome.manager.core.network.BuildConfig
import com.awesome.manager.core.network.ErrorResponse
import com.awesome.manager.core.network.NetworkError
import com.awesome.manager.core.network.model.request.Authorization
import com.awesome.manager.core.network.model.request.RefreshTokenBody
import com.awesome.manager.core.network.model.response.AuthNetwork
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.RequestTimeout
import io.ktor.http.HttpStatusCode.Companion.TooManyRequests
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.UnknownHostException
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideChucker(@ApplicationContext context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .createShortcut(true)
            .build()

    }

    @Provides
    @Singleton
    fun provideKtorClient(
        authPreferencesDataStore: AuthPreferencesDataStore,
        chuckerInterceptor: ChuckerInterceptor
    ) = HttpClient(OkHttp.create { addInterceptor(chuckerInterceptor) }) {

        defaultRequest {
            contentType(ContentType.Application.Json)
            header("apikey", BuildConfig.API_KEY)
            url {
                protocol = URLProtocol.HTTPS
                host = BuildConfig.BASE_URL
            }
        }

        expectSuccess = true

        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, request ->
                throw when (exception) {
                    is UnknownHostException -> NetworkError.InternalServerError()
                    is ConnectTimeoutException, is SocketTimeoutException -> NetworkError.ConnectionError()
                    is JsonConvertException -> NetworkError.ConvertDataError()
                    is ServerResponseException -> NetworkError.InternalServerError()
                    is RedirectResponseException -> NetworkError.ConnectionError()
                    is ClientRequestException -> when (exception.response.status) {
                        Unauthorized -> NetworkError.Unauthorized()
                        Forbidden -> NetworkError.Forbidden()
                        RequestTimeout -> NetworkError.RequestTimeout()
                        TooManyRequests -> NetworkError.TooManyRequests()
                        BadRequest -> NetworkError.BadRequest(
                            exception.response.body<ErrorResponse>().getErrorMessage()
                        )

                        else -> NetworkError.OtherError(
                            exception.response.body<ErrorResponse>().getErrorMessage()
                        )
                    }

                    else -> NetworkError.ConnectionError()
                }
            }
        }

        install(Resources)

        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Auth) {
            bearer {
                loadTokens {
                    val authToken =
                        authPreferencesDataStore.returnAccessToken().firstOrNull().orEmpty()
                    val refreshToken =
                        authPreferencesDataStore.returnRefreshToken().firstOrNull().orEmpty()
                    BearerTokens(authToken, refreshToken)
                }
                refreshTokens {
                    val authNetwork =
                        client
                            .post(Authorization.RefreshToken()) {
                                setBody(RefreshTokenBody(oldTokens?.refreshToken.orEmpty()))
                                markAsRefreshTokenRequest()
                            }
                            .body<AuthNetwork>()
                    authNetwork.run {
                        authPreferencesDataStore.updateToken(
                            accessToken = accessToken, refreshToken = refreshToken,
                        )
                        BearerTokens(accessToken, refreshToken)
                    }
                }

                sendWithoutRequest { request ->
                    request.url.host == BuildConfig.BASE_URL
                }
            }
        }

        install(Logging) {
            level = LogLevel.ALL
        }

    }

}