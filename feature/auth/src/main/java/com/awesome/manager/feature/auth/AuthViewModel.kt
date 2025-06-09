package com.awesome.manager.feature.auth

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.designsystem.component.text.isValidEmail
import com.awesome.manager.core.designsystem.component.text.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val EMAIL_TEXT: String = "EMAIL_TEXT"
private const val PASSWORD_TEXT: String = "PASSWORD_TEXT"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val emailTextFieldState: TextFieldState = TextFieldState()
    val passwordTextFieldState: TextFieldState = TextFieldState()

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.InitState)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        restoreSavedData()
        syncInputValidation()
    }

    private fun restoreSavedData() {
        emailTextFieldState
            .setTextAndPlaceCursorAtEnd(savedStateHandle.get<String>(EMAIL_TEXT).orEmpty())
        passwordTextFieldState
            .setTextAndPlaceCursorAtEnd(savedStateHandle.get<String>(PASSWORD_TEXT).orEmpty())
    }

    private fun syncInputValidation() {

        val email = snapshotFlow { emailTextFieldState.text.toString() }
        val password = snapshotFlow { passwordTextFieldState.text.toString() }

        viewModelScope.launch {
            combine(email, password) { email, password ->

                savedStateHandle[EMAIL_TEXT] = email
                savedStateHandle[PASSWORD_TEXT] = password

                val invalidEmail = !email.isValidEmail()
                val invalidPassword = !password.isValidPassword()

                when {
                    invalidEmail || invalidPassword -> AuthState.ErrorInvalidInput(
                        invalidEmail = invalidEmail,
                        invalidPassword = invalidPassword
                    )

                    else -> AuthState.ValidatedInput
                }
            }
                .drop(1)
                .collect { authState -> _authState.update { authState } }
        }

    }


    fun login() {
        viewModelScope.launch {
            authRepository.login(
                email = emailTextFieldState.text.toString(),
                password = passwordTextFieldState.text.toString()
            ).collectLatest { requestState ->
                val authState = when (requestState) {
                    is AmState.Loading -> AuthState.Loading
                    is AmState.Success<*> -> AuthState.LoggedInSuccessfully
                    is AmState.Error -> when (requestState.amUIError) {
                        is AmUIError.BadRequest -> AuthState.ErrorRequestCertification
                        is AmUIError.ConnectionUIError -> AuthState.ErrorRequestConnection
                        else -> AuthState.ErrorRequestUnknown
                    }
                }
                _authState.update { authState }
            }
        }
    }


}