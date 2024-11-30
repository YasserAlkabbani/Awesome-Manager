package com.awesome.manager.feature.auth

import com.awesome.manager.core.ui.actions.main.ActionsManager
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

private const val EMAIL: String = "EMAIL"
private const val PASSWORD: String = "PASSWORD"

class AuthScreenState(
    override val setString: String.(value: String) -> Unit,
    override val getString: String.(defaultValue: String) -> StateFlow<String>,
    val login: (String, String) -> Unit,
) : ActionsManager() {

    val email: StateFlow<String> = EMAIL.getString("")
    fun onUpdateEmail(email: String) = EMAIL.setString(email)

    val password: StateFlow<String> = PASSWORD.getString("")
    fun onUpdatePassword(password: String) = PASSWORD.setString(password)


    val authUI = combine(email, password) { email, password ->
        processUIState(email = email, password = password)
    }

    private fun processUIState(email: String, password: String) {
        val validEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val validPassword = password.length > 5
        when {
            email.isBlank() && password.isBlank() -> dynamicFabWelcome()
            !validEmail -> dynamicFabInvalidEmail()
            !validPassword -> dynamicFabInvalidPassword()
            else -> dynamicFabLogin { login(email, password) }
        }
    }

}

