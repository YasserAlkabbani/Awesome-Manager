package com.awesome.manager.feature.auth

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import com.awesome.manager.core.common.ProcessStates
import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.designsystem.component.asFlow
import com.awesome.manager.core.designsystem.component.text.isValidEmail
import com.awesome.manager.core.designsystem.component.text.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val EMAIL_TEXT: String = "EMAIL_TEXT"
private const val PASSWORD_TEXT: String = "PASSWORD_TEXT"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.InitState)
    val authState: StateFlow<AuthState> = _authState
    fun AuthState.update() = _authState.update { this }

    private val _authEvent: MutableStateFlow<AuthEvents> = MutableStateFlow(AuthEvents.Idle)
    val authEvent: StateFlow<AuthEvents> = _authEvent.asStateFlow()
    fun resetAuthEvent() = _authEvent.update { AuthEvents.Idle }

    val emailTextFieldState: TextFieldState = savedStateHandle.saveable(
        key = EMAIL_TEXT,
        saver = TextFieldState.Saver,
        init = { TextFieldState() }
    )
    val passwordTextFieldState: TextFieldState = savedStateHandle.saveable(
        key = PASSWORD_TEXT,
        saver = TextFieldState.Saver,
        init = { TextFieldState() }
    )

    val syncAuthState = combine(
        emailTextFieldState.asFlow(),
        passwordTextFieldState.asFlow()
    ) { email, password ->

        val isValidEmail = email.isValidEmail()
        val isValidPassword = password.isValidPassword()

        when {
            email.isBlank() || password.isBlank() -> AuthState.InitState
            isValidEmail && isValidPassword -> AuthState.ValidatedInput(
                email = email,
                password = password
            )

            else -> AuthState.ErrorInvalidInput(
                invalidEmail = !isValidEmail,
                invalidPassword = !isValidPassword
            )
        }

    }.onEach { newAuthState -> newAuthState.update() }

    init {
        viewModelScope.launch { syncAuthState.collect() }
    }

    fun tryLogin()=viewModelScope.launch {
        val authState=syncAuthState.first()
        if (authState is AuthState.ValidatedInput) login(authState)
    }

    fun login(authState: AuthState.ValidatedInput) {
        viewModelScope.launch {
            authRepository
                .login(
                    email = authState.email,
                    password = authState.password
                )
                .collectLatest { requestState ->
                    val newAuthState = when (requestState) {
                        is ProcessStates.Loading -> AuthState.Loading
                        is ProcessStates.Success<*> -> AuthState.LoggedInSuccessfully
                        is ProcessStates.Error -> when (requestState.amUIError) {
                            is AmUIError.BadRequest -> AuthState.ErrorRequestCertification
                            is AmUIError.ConnectionUIError -> AuthState.ErrorRequestConnection
                            else -> AuthState.ErrorRequestUnknown
                        }
                    }
                    newAuthState.update()
                }
        }
    }

}