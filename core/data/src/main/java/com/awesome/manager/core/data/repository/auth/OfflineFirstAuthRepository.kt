package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.data.extention.amRequest
import com.awesome.manager.core.datastore.AuthPreferencesDataStore
import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import com.awesome.manager.core.network.model.request.LoginRequest
import com.awesome.manager.core.network.model.request.SignupRequest
import com.awesome.manager.core.network.model.response.AuthNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class OfflineFirstAuthRepository @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val authPreferencesDataStore: AuthPreferencesDataStore,
) : AuthRepository {

    override suspend fun login(email: String, password: String) =
        amRequest {
            val authNetwork = authNetworkDataSource.login(
                LoginRequest(email = email, password = password)
            )
            updateToken(authNetwork)
        }

    override suspend fun signUp(email: String, password: String) =
        amRequest {
            val authUserNetwork = authNetworkDataSource.signUp(
                SignupRequest(email = email, password = password)
            )
//            response.identities.isEmpty()
        }

    override suspend fun logout() = amRequest {
        authPreferencesDataStore.clearAuth()
        authNetworkDataSource.logout()
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