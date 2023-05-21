package com.awesome.manager.core.data.repository.auth

import android.util.Log
import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.network.AuthNetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class OfflineFirstAuthRepository @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource
):AuthRepository {
    override suspend fun login(email: String, password: String): Flow<AmResult<String>> =
        amRequest {
            authNetworkDataSource.login(email,password)
        }

    override suspend fun signUp(email: String, password: String) =
        amRequest {
            authNetworkDataSource.signUp(email,password)
        }
}