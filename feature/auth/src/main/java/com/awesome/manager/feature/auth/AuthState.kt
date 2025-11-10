package com.awesome.manager.feature.auth


sealed interface AuthState {

    data object InitState : AuthState

    data class ValidatedInput(
        val email:String,
        val password:String
    ) : AuthState

    data object Loading : AuthState

    data object LoggedInSuccessfully : AuthState

    data class ErrorInvalidInput(
        val invalidEmail: Boolean,
        val invalidPassword: Boolean
    ) : AuthState

    data object ErrorRequestCertification : AuthState
    data object ErrorRequestConnection : AuthState
    data object ErrorRequestUnknown : AuthState

}

sealed interface AuthEvents{
    data object Idle:AuthEvents
}