package com.awesome.manager.feature.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val authScreenState: AuthScreenState = AuthScreenState(
        savedStateHandle = savedStateHandle,
        asStateFlow = {
            stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = true)
        },
        login = ::login, register = ::register, resetPassword = ::resetPassword
    )

    private fun login() {
        val email = authScreenState.email.value
        val password = authScreenState.password.value
        viewModelScope.launch {
            authRepository.login(email, password).collectLatest { amResult ->
                authScreenState.updateStateBasedOnResult(
                    amResult = amResult, onSuccess = {},
                )
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            authRepository.signUp(authScreenState.email.value, authScreenState.password.value)
                .collectLatest { amResult ->
                    authScreenState.updateStateBasedOnResult(
                        amResult = amResult,
                        onSuccess = { authScreenState.showBottomSheetMessage(MessageType.RegisterSuccess) },
                    )
                }
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            authRepository.signUp(authScreenState.email.value, authScreenState.password.value)
                .collectLatest { amResult ->
                    authScreenState.updateStateBasedOnResult(
                        amResult = amResult,
                        onSuccess = { authScreenState.showBottomSheetMessage(MessageType.ResetPasswordSuccess) },
                    )
                }
        }
    }


}