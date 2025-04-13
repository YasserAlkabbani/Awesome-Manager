package com.awesome.manager.feature.auth

import com.awesome.manager.core.common.AmUIError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

private const val EMAIL: String = "EMAIL"
private const val PASSWORD: String = "PASSWORD"

class AuthScreenState(
    val setString: String.(value: String) -> Unit,
    val getString: String.(defaultValue: String) -> StateFlow<String>,
    val login: (String, String) -> Unit,
) {

    val email: StateFlow<String> = EMAIL.getString("")
    fun onUpdateEmail(email: String) = EMAIL.setString(email)

    val password: StateFlow<String> = PASSWORD.getString("")
    fun onUpdatePassword(password: String) = PASSWORD.setString(password)

    private val _authError: MutableStateFlow<AuthError> = MutableStateFlow(AuthError.NON)
    val authError: StateFlow<AuthError> = _authError.asStateFlow()

    private val _amUIError: MutableStateFlow<AmUIError> = MutableStateFlow(AmUIError.NoError)
    val amUIError: StateFlow<AmUIError> = _amUIError.asStateFlow()
    fun setUIError(amUIError: AmUIError) = _amUIError.update { amUIError }
    fun doneUIError() =_amUIError.update { AmUIError.NoError }

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()
    fun setLoading(isLoading: Boolean) = _loading.update { isLoading }

    fun syncAuthUIState() = combine(email, password) { email, password ->
        val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isValidPassword = password.length > 5
        when {
            email.isBlank() && password.isBlank() -> _authError.update { AuthError.EMAIL_PASSWORD }
            !isValidEmail -> _authError.update { AuthError.EMAIL }
            !isValidPassword -> _authError.update { AuthError.PASSWORD }
            else -> _authError.update { AuthError.NON }
        }
    }

}

enum class AuthError {
    NON, EMAIL, PASSWORD, EMAIL_PASSWORD
}

