package com.awesome.manager.core.datastore

import android.net.Uri
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
    private val currentUserEmailKey = stringPreferencesKey("current_email")

    suspend fun updateToken(
        accessToken: String, refreshToken: String,
        currentUserId: String, email: String
    ) {
        authDataStore.edit {
            it[accessTokenKey] = Uri.encode(accessToken)
            it[refreshTokenKey] = Uri.encode(refreshToken)
            it[currentUserIdKey] = Uri.encode(currentUserId)
            it[currentUserEmailKey] = Uri.encode(email)
        }
    }

    suspend fun clearAuth() {
        authDataStore.edit { it.clear() }
    }

    fun returnAccessToken() = authDataStore.data.map {
        Uri.decode(it[accessTokenKey])
    }

    fun returnRefreshToken() = authDataStore.data.map {
        Uri.decode(it[refreshTokenKey])
    }

    fun returnCurrentUserId() = authDataStore.data.map {
        Uri.decode(it[currentUserIdKey])
    }

    fun returnCurrentUserEmail() = authDataStore.data.map {
        Uri.decode(it[currentUserEmailKey])
    }


}