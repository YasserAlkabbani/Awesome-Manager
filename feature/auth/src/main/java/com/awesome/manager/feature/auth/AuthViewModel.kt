package com.awesome.manager.feature.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val authScreenState: AuthScreenActions = AuthScreenActions(
        login = ::login,
        register = ::register,
        resetPassword = ::resetPassword,
        savedStateHandle = savedStateHandle,
    )

    private val errorState = authScreenState.syncValidation(viewModelScope)
    private val isValidateData: Boolean
        get() = errorState.value

    private fun login() {
        viewModelScope.launch {
            if (isValidateData) {
                authScreenState.apply {
                    authRepository
                        .login(email = email.value, password = password.value)
                        .processRequest(onSuccess = {}, onError = {
                            when (it) {
                                is AmUIError.BadRequest -> dynamicFabMessage(
                                    dynamicFabText = DynamicFabText.InvalidLoginCredential
                                )

                                else -> addError(it)
                            }
                        })
                }
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            if (isValidateData) {
                authScreenState.apply {
                    authRepository
                        .signUp(email = email.value, password = password.value)
                        .processRequest(onSuccess = authScreenState::showAccountCreatedBottomSheet)
                }
            }
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            if (isValidateData) {
                authScreenState.apply {
                    authRepository
                        .signUp(email = email.value, password = password.value)
                        .processRequest(onSuccess = authScreenState::showPasswordRestedBottomSheet)
                }
            }
        }
    }


}