package com.awesome.manager.core.network.di

import android.content.Context
import android.util.Log
import com.awesome.manager.core.datastore.AuthPreferencesDataStore
import com.awesome.manager.core.network.BuildConfig
import com.awesome.manager.core.network.asResult
import com.awesome.manager.core.network.model.AuthNetwork
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.resources.Resource
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Serializable
private data class RefreshTokenBody(@SerialName("refresh_token") val refreshToken: String)

@Resource("auth/v1/")
private class AuthRequest {
    @Resource("token")
    class RefreshToken(
        val parent: AuthRequest = AuthRequest(),
        val grant_type: String = "refresh_token"
    )
}

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


        engine {}

        defaultRequest {
            contentType(ContentType.Application.Json)
            header("apikey", BuildConfig.API_KEY)
            url {
                protocol = URLProtocol.HTTPS
                host = BuildConfig.BASE_URL
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
                    val authToken = authPreferencesDataStore.returnAccessToken().first().orEmpty()
                    val refreshToken =
                        authPreferencesDataStore.returnRefreshToken().first().orEmpty()
                    BearerTokens(authToken, refreshToken)
                }
                refreshTokens {
                    val refreshTokenResult = client.post(AuthRequest.RefreshToken()) {
                        setBody(RefreshTokenBody(oldTokens?.refreshToken.orEmpty()))
                    }.asResult<AuthNetwork>()
                    val accessToken = refreshTokenResult.accessToken
                    val refreshToken = refreshTokenResult.refreshToken
                    val currentUserId = refreshTokenResult.authUserNetwork.id
                    authPreferencesDataStore.updateToken(
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                        currentUserId = currentUserId
                    )
                    BearerTokens(accessToken, refreshToken)
                }
            }
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

}