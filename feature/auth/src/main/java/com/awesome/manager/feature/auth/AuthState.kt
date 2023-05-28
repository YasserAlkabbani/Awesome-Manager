package com.awesome.manager.feature.auth

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.common.AmResult
import com.awesome.manager.core.designsystem.text.AmText
import com.awesome.manager.core.designsystem.text.asAmText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val EMAIL_KEY:String="EMAIL"
const val PASSWORD_KEY:String="PASSWORD"

class AuthScreenState (
    private val savedStateHandle: SavedStateHandle,
    val login:()->Unit, val signUp:()->Unit
){

    val email:StateFlow<String> =savedStateHandle.getStateFlow(EMAIL_KEY,"yasseralkabbani1411989@gmail.com")
    fun updateEmail(newEmail:String)=savedStateHandle.set(EMAIL_KEY,newEmail)

    val password:StateFlow<String> =savedStateHandle.getStateFlow(PASSWORD_KEY,"123456")
    fun updatePassword(newPassword:String)=savedStateHandle.set(PASSWORD_KEY,newPassword)

    private val _navigateToHome:MutableStateFlow<Unit?> =MutableStateFlow(null)
    val navigateToHome:StateFlow<Unit?> =_navigateToHome.asStateFlow()
    fun startNavigateToHome(){_navigateToHome.update { }}
    fun doneNavigateToHome(){_navigateToHome.update { null }}

    private val _authScreenMainState:MutableStateFlow<AuthScreenMainState> =MutableStateFlow(AuthScreenMainState.Idle)
    val authScreenMainState:StateFlow<AuthScreenMainState> =_authScreenMainState.asStateFlow()
    fun updateStateBasedOnResult(amResult: AmResult<Any>, doOnSuccess:()->Unit){
        when(amResult){
            is AmResult.Error -> _authScreenMainState.update { AuthScreenMainState.Error(amResult.throwable.message.orEmpty().asAmText()) }
            is AmResult.Loading -> _authScreenMainState.update { AuthScreenMainState.Loading }
            is AmResult.Success -> {
                _authScreenMainState.update { AuthScreenMainState.Idle }
                doOnSuccess()
            }
        }
    }

}

sealed class  AuthScreenMainState{
    object Idle: AuthScreenMainState()
    object Loading: AuthScreenMainState()
    data class Error(val amText: AmText): AuthScreenMainState()
}