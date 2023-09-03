package com.awesome.manager.feature.auth

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.common.AmError
import com.awesome.manager.core.common.AmResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import timber.log.Timber

private const val EMAIL_KEY:String="EMAIL"
private const val PASSWORD_KEY:String="PASSWORD"

class AuthScreenState (
    private val savedStateHandle: SavedStateHandle,
    private val asStateFlow:Flow<Boolean>.()->StateFlow<Boolean>,
    val login:()->Unit, val register:()->Unit, val resetPassword:()->Unit
){

    val email:StateFlow<String> =savedStateHandle.getStateFlow(EMAIL_KEY,"yasseralkabbani1411989@gmail.com")
    val validateEmail=email.map { android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() }.asStateFlow()
    fun updateEmail(newEmail:String)=savedStateHandle.set(EMAIL_KEY,newEmail)

    val password:StateFlow<String> =savedStateHandle.getStateFlow(PASSWORD_KEY,"123456")
    val validatePassword=password.map { it.length>5 }.asStateFlow()
    fun updatePassword(newPassword:String)=savedStateHandle.set(PASSWORD_KEY,newPassword)



    private val _authScreenMainState:MutableStateFlow<AuthScreenMainState> =MutableStateFlow(AuthScreenMainState.Idle)
    val authScreenMainState:StateFlow<AuthScreenMainState> =_authScreenMainState.asStateFlow()
    fun updateStateBasedOnResult(
        amResult: AmResult<Any>, onSuccess:()->Unit,
    ){
        Timber.d("TEST_AUTH_STATE $amResult")
        when(amResult){
            is AmResult.Error -> when(val amError=amResult.amError){
                is AmError.BadRequest -> _authScreenMainState.update {AuthScreenMainState.BottomSheetMessage(
                    messageType = MessageType.LoginError,
                )}
                is AmError.OtherError -> _authScreenMainState.update {AuthScreenMainState.BottomSheetMessage(
                    messageType = MessageType.OtherError(
                        errorMessage = amError.errorMessage,
                        onDone = ::setMainStateAsIdle,
                    ),
                )}
                AmError.Unauthorized  ->
                    _authScreenMainState.update {AuthScreenMainState.BottomSheetMessage(
                    messageType = MessageType.OtherError(
                        errorMessage = amError.message,
                        onDone = ::setMainStateAsIdle,
                    ),
                )}

                AmError.ConnectionError -> _authScreenMainState.update {AuthScreenMainState.BottomSheetMessage(
                    messageType = MessageType.ConnectionError,
                )}
            }
            is AmResult.Loading -> _authScreenMainState.update {AuthScreenMainState.Loading}
            is AmResult.Success -> onSuccess()
        }

    }
    fun setMainStateAsIdle(){_authScreenMainState.update { AuthScreenMainState.Idle }}
    fun showBottomSheetMessage(messageType:MessageType){_authScreenMainState.update { AuthScreenMainState.BottomSheetMessage(messageType) }}

}

sealed class  AuthScreenMainState{
    object Idle: AuthScreenMainState()
    object Loading: AuthScreenMainState()
    data class BottomSheetMessage(val messageType:MessageType): AuthScreenMainState()
    fun otherError()=(this as? BottomSheetMessage)?.messageType?.let { it as? MessageType.OtherError }
}

sealed class MessageType{
    object LoginError:MessageType()
    object RegisterSuccess:MessageType()
    object ResetPasswordSuccess:MessageType()
    object ConnectionError:MessageType()
    data class OtherError(val errorMessage:String?, val onDone:()->Unit):MessageType()
}