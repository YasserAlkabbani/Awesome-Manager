package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.datastore.AuthPreferencesDataStore
import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class OfflineFirstAuthRepository @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val authPreferencesDataStore: AuthPreferencesDataStore
) : AuthRepository {

    override fun isLogin(): Flow<Boolean> =
        authPreferencesDataStore.returnAccessToken().map { it.isNullOrBlank().not() }

    override suspend fun refreshUserInfo(){
        amRequest {
            authNetworkDataSource.refreshUser().toString()
        }.collect()
    }

    override suspend fun login(email: String, password: String): Flow<AmResult<String>> =
        amRequest {
            val response = authNetworkDataSource.login(email, password)
            authPreferencesDataStore.updateToken(
                response.accessToken.orEmpty(),
                response.refreshToken.orEmpty()
            )
            response.user.toString()
        }

    override suspend fun signUp(email: String, password: String) = amRequest {
        authNetworkDataSource.signUp(email, password)
    }

}