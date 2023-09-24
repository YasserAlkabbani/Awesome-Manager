package com.awesome.manager.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.awesome.manager.core.datastore.di.DataStoreAuth
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthPreferencesDataStore @Inject constructor(@DataStoreAuth private val authDataStore: DataStore<Preferences>) {

    private val accessTokenKey = stringPreferencesKey("access_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
    private val currentUserIdKey = stringPreferencesKey("current_user_id")

    suspend fun updateToken(accessToken: String, refreshToken: String, currentUserId: String) {
        authDataStore.edit {
            it[accessTokenKey] = accessToken
            it[refreshTokenKey] = refreshToken
            it[currentUserIdKey] = currentUserId
        }
    }

    suspend fun clearAuth() {
        authDataStore.edit {
            it[accessTokenKey] = ""
            it[refreshTokenKey] = ""
            it[currentUserIdKey] = ""
        }
    }

    fun returnAccessToken() = authDataStore.data.map {
        it[accessTokenKey]
    }

    fun returnRefreshToken() = authDataStore.data.map {
        it[refreshTokenKey]
    }

    fun returnCurrentUserId() = authDataStore.data.map {
        it[currentUserIdKey]
    }

}