package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.datastore.AuthPreferencesDataStore
import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import com.awesome.manager.core.network.model.AuthNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class OfflineFirstAuthRepository @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val authPreferencesDataStore: AuthPreferencesDataStore,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Flow<AmResult<Boolean>> =
        amRequest {
            val authNetwork = authNetworkDataSource.login(email, password)
            updateToken(authNetwork)
            true
        }

    override suspend fun signUp(email: String, password: String): Flow<AmResult<Boolean>> =
        amRequest {
            val response = authNetworkDataSource.signUp(email, password)
            response.identities.isEmpty()
        }

    override suspend fun logout(): Flow<AmResult<Unit>> = amRequest {
        authPreferencesDataStore.clearAuth()
        authNetworkDataSource.logout()
    }

    override suspend fun refreshUserInfo() {
        amRequest {
            authNetworkDataSource.refreshUser().let { authNetwork ->
                updateToken(authNetwork)
            }
        }.collect()
    }

    override suspend fun updateToken(authNetwork: AuthNetwork) {
        authPreferencesDataStore.updateToken(
            accessToken = authNetwork.accessToken,
            refreshToken = authNetwork.refreshToken,
            currentUserId = authNetwork.authUserNetwork.id,
            email = authNetwork.authUserNetwork.email
        )
    }

    override fun isLogin(): Flow<Boolean> =
        authPreferencesDataStore.returnAccessToken()
            .map { it.isNullOrBlank().not() }
            .distinctUntilChanged()

    override fun currentUserId(): Flow<String> =
        authPreferencesDataStore.returnCurrentUserId().filterNotNull()

    override fun currentUserEmail(): Flow<String> =
        authPreferencesDataStore.returnCurrentUserEmail().filterNotNull()

}