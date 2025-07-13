package com.awesome.manager.feature.auth

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.designsystem.component.asFlow
import com.awesome.manager.core.designsystem.component.text.isValidEmail
import com.awesome.manager.core.designsystem.component.text.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val EMAIL_TEXT: String = "EMAIL_TEXT"
private const val PASSWORD_TEXT: String = "PASSWORD_TEXT"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.InitState)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

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

    init {
        syncAuthState()
    }

    private fun syncAuthState() {

        viewModelScope.launch {
            combine(
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

            }
                .collect { authState -> _authState.update { authState } }
        }

    }


    fun login() {
        viewModelScope.launch {
            suspend fun AmState<Unit>.asAuthState() = withContext(Dispatchers.Default) {
                val newAuthState = when (this@asAuthState) {
                    is AmState.Loading -> AuthState.Loading
                    is AmState.Success<*> -> AuthState.LoggedInSuccessfully
                    is AmState.Error -> when (amUIError) {
                        is AmUIError.BadRequest -> AuthState.ErrorRequestCertification
                        is AmUIError.ConnectionUIError -> AuthState.ErrorRequestConnection
                        else -> AuthState.ErrorRequestUnknown
                    }
                }
                _authState.update { newAuthState }
            }

            suspend fun AuthState.ValidatedInput.login() =
                authRepository
                    .login(email = email, password = password)
                    .collectLatest { requestState -> requestState.asAuthState() }

            (authState.value as? AuthState.ValidatedInput)?.login()
        }
    }


}