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
    private val authRepository: AuthRepository,
) : ViewModel() {

    val authScreenState: AuthScreenState = AuthScreenState(
        login = ::login, register = ::register, resetPassword = ::resetPassword
    )
    private val authData: AmAuthData get() = authScreenState.authData.value
    private val isValidateData: Boolean get() = authData.validateData
    private val email: String get() = authData.password
    private val password: String get() = authData.email

    private fun login() {
        viewModelScope.launch {
            if (isValidateData) {
                authRepository.login(email, password).collectLatest { amResult ->
                    authScreenState.updateStateBasedOnResult(
                        amResult = amResult, onSuccess = {},
                    )
                }
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            if (isValidateData) {
                authRepository.signUp(email, password).collectLatest { amResult ->
                    authScreenState.updateStateBasedOnResult(
                        amResult = amResult,
                        onSuccess = authScreenState::showAccountCreatedBottomSheet,
                    )
                }
            }
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            if (isValidateData) {
                authRepository.signUp(email, password).collectLatest { amResult ->
                    authScreenState.updateStateBasedOnResult(
                        amResult = amResult,
                        onSuccess = authScreenState::showPasswordRestedBottomSheet,
                    )
                }
            }
        }
    }


}