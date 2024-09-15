package com.awesome.manager.feature.auth

import com.awesome.manager.core.data.extention.AmResult
import com.awesome.manager.core.designsystem.actions.main.StateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthScreenState(
    val login: () -> Unit, val register: () -> Unit,
    val resetPassword: () -> Unit,
) : StateManager() {

    private val _authData: MutableStateFlow<AmAuthData> = MutableStateFlow(AmAuthData())
    val authData: StateFlow<AmAuthData> = _authData.asStateFlow()
    fun updateEmail(email: String) = _authData.update { it.copy(email = email) }
    fun updatePassword(password: String) = _authData.update { it.copy(password = password) }

    fun updateStateBasedOnResult(
        amResult: AmResult<Any>, onSuccess: () -> Unit,
    ) {
        setLoading(amResult is AmResult.Loading)
//        when (amResult) {
//            is AmResult.Error -> when (val amError = amResult.amError) {
//
//                is AmError.BadRequest -> {
//                    showAuthErrorBottomSheet(
//                        errorMessage = amError.errorMessage,
//                        onCreateAccount = {
//                            register()
//                            dismissBottomSheet()
//                        },
//                        editCredentials = ::dismissBottomSheet
//                    )
//                }
//
//                is AmError.OtherError -> {
//                    showCustomErrorMessage(errorMessage = amError.errorMessage.orEmpty())
//                }
//
//                AmError.Unauthorized -> showCustomErrorMessage(errorMessage = amError.message.orEmpty())
//
//                AmError.ConnectionError -> showConnectionErrorBottomSheet()
//                AmError.UnknownError -> {}
//            }
//
//            is AmResult.Loading -> Unit
//            is AmResult.Success -> onSuccess()
//        }

    }

}

data class AmAuthData(
    val email: String = "",
    val password: String = "",
) {
    val validateEmail: Boolean get() = email.isValidEmail()
    val validatePassword: Boolean get() = password.isValidPassword()
    val validateData: Boolean get() = validateEmail && validatePassword
    val noData: Boolean get() = email.isBlank() && password.isBlank()
}

fun String.isValidEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword() = this.length > 5

fun String.limitName() = this.substringBefore(" ").take(10)