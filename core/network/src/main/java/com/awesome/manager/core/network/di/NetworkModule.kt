package com.awesome.manager.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import io.ktor.http.set
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKtorClient() = HttpClient(OkHttp) {

        engine {}

        defaultRequest {
            url {
                protocol= URLProtocol.HTTPS
                host = "ksvuzjbwpkqlzeithzjg.supabase.co/"
            }
        }

        install(Resources)

        install(ContentNegotiation) {
            json(
                json = Json {
                    isLenient = true
                    ignoreUnknownKeys = false
                }
            )
        }

        install(Auth) {
            bearer {

            }
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
//            filter { request -> request.url.host.contains("ktor.io") }
//            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }

    }

}