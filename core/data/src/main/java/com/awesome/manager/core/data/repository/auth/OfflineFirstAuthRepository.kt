package com.awesome.manager.core.data.repository.auth

import com.awesome.manager.core.data.extention.requestUIState
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.database.dao.UserDao
import com.awesome.manager.core.model.AmUser
import com.awesome.manager.core.network.datasource.AuthNetworkDataSource
import com.awesome.manager.core.network.model.request.LoginRequest
import com.awesome.manager.core.network.model.request.SignupRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class OfflineFirstAuthRepository @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val userDao: UserDao,
) : AuthRepository {

    override suspend fun login(email: String, password: String) =
        requestUIState {
            val authNetwork = authNetworkDataSource.login(
                LoginRequest(email = email, password = password)
            )
            val userNetwork = authNetwork.asUserNetwork()
            userDao.upsertUser(userNetwork.asEntity())
        }

    override suspend fun signUp(email: String, password: String) =
        requestUIState {
            val authUserNetwork = authNetworkDataSource.signUp(
                SignupRequest(email = email, password = password)
            )
//            response.identities.isEmpty()
        }

    override suspend fun logout() = requestUIState {
        authNetworkDataSource.logout()
    }

    override fun isLogin(): Flow<Boolean> =
        userDao.returnCurrentUser()
            .map { it != null }
            .distinctUntilChanged()

    override fun currentUser(): Flow<AmUser> =
        userDao.returnCurrentUser()
            .filterNotNull()
            .map { it.asModel() }


}