package com.awesome.manager.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.awesome.manager.core.datastore.di.DataStoreAuth
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthPreferencesDataStore @Inject constructor(@DataStoreAuth private val authDataStore:DataStore<Preferences>){

    private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")

    suspend fun updateAuthToken(newToken:String){
        authDataStore.edit {
            it[AUTH_TOKEN_KEY]=newToken
        }
    }

    suspend fun clearAuthToken(){
        authDataStore.edit {
            it[AUTH_TOKEN_KEY]=""
        }
    }

    fun returnAuthToken()=authDataStore.data.map {
        it[AUTH_TOKEN_KEY]
    }

}