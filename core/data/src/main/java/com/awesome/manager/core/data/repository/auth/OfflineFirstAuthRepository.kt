package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.datastore.AuthPreferencesDataStore
import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class OfflineFirstAuthRepository @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val authPreferencesDataStore: AuthPreferencesDataStore
) : AuthRepository {


    override suspend fun refreshUserInfo(){
        amRequest {
            authNetworkDataSource.refreshUser().toString()
        }.collect()
    }

    override suspend fun login(email: String, password: String): Flow<AmResult<Boolean>> =
        amRequest {
            val response = authNetworkDataSource.login(email, password)
            authPreferencesDataStore.updateToken(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
                currentUserId = response.authUserNetwork.id
            )
            true
        }

    override suspend fun signUp(email: String, password: String): Flow<AmResult<Boolean>> = amRequest {
        val response=authNetworkDataSource.signUp(email, password)
        response.identities.isEmpty()
    }

    override fun isLogin(): Flow<Boolean> =
        authPreferencesDataStore.returnAccessToken().map { it.isNullOrBlank().not() }

    override suspend fun returnCurrentUserId(): String? =
        authPreferencesDataStore.returnCurrentUserId().firstOrNull()

}