package com.awesome.manager.feature.auth

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabText
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabButton
import com.awesome.manager.core.ui.actions.main.ActionsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

private const val EMAIL: String = "EMAIL"
private const val PASSWORD: String = "PASSWORD"

class AuthScreenActions(
    val login: () -> Unit,
    val register: () -> Unit,
    val resetPassword: () -> Unit,
    private val savedStateHandle: SavedStateHandle,
) : ActionsManager() {

    val email: StateFlow<String> = savedStateHandle.getStateFlow(EMAIL, "")
    fun onUpdateEmail(email: String) = savedStateHandle.set(EMAIL, email)

    val password: StateFlow<String> = savedStateHandle.getStateFlow(PASSWORD, "")
    fun onUpdatePassword(password: String) = savedStateHandle.set(PASSWORD, password)


    fun syncValidation(coroutineScope: CoroutineScope) =
        combine(email, password) { email, password ->
            when {
                email.isBlank() && password.isBlank() -> {
                    dynamicFabMessage(
                        dynamicFabText = DynamicFabText.WelcomeBack
                    )
                    false
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    dynamicFabMessage(
                        dynamicFabText = DynamicFabText.InvalidEmail
                    )
                    false
                }
                password.length < 5  -> {
                    dynamicFabMessage(
                        dynamicFabText = DynamicFabText.InvalidPassword
                    )
                    false
                }
                else -> {
                    dynamicFabButton(
                        dynamicFabButton = DynamicFabButton.Login(onClick = login)
                    )
                    true
                }
            }
        }.stateIn(coroutineScope, SharingStarted.Eagerly, false)

}