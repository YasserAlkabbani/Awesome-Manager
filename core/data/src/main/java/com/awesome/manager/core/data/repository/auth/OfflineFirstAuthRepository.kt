package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.datastore.AuthPreferencesDataStore
import com.awesome.manager.core.network.AuthNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class OfflineFirstAuthRepository @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val authPreferencesDataStore: AuthPreferencesDataStore
):AuthRepository {

    override fun isLogin():Flow<Boolean> =authPreferencesDataStore.returnAuthToken().map { it.isNullOrBlank().not() }

    override suspend fun login(email: String, password: String): Flow<AmResult<String>> =
        amRequest {
            val loginResponse=authNetworkDataSource.login(email,password)
            authPreferencesDataStore.updateAuthToken(loginResponse.accessToken.orEmpty())
            loginResponse.user.toString()
        }

    override suspend fun signUp(email: String, password: String) =
        amRequest {
            authNetworkDataSource.signUp(email,password)
        }
}