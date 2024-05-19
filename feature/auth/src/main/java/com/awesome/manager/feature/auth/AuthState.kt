package com.awesome.manager.feature.auth

import com.awesome.manager.core.common.extentions.isValidEmail
import com.awesome.manager.core.common.extentions.isValidPassword
import com.awesome.manager.core.common.results.AmError
import com.awesome.manager.core.common.results.AmResult
import com.awesome.manager.core.designsystem.ui_actions.main.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class AuthScreenState(
    val login: () -> Unit, val register: () -> Unit, val resetPassword: () -> Unit,
) : MainState() {

    private val _authData: MutableStateFlow<AmAuthData> = MutableStateFlow(AmAuthData())
    val authData: StateFlow<AmAuthData> = _authData
    fun updateEmail(email: String) = _authData.update { it.copy(email = email) }
    fun updatePassword(password: String) = _authData.update { it.copy(password = password) }

    fun updateStateBasedOnResult(
        amResult: AmResult<Any>, onSuccess: () -> Unit,
    ) {
        stopLoading()
        when (amResult) {
            is AmResult.Error -> when (val amError = amResult.amError) {

                is AmError.BadRequest -> {
                    showAuthErrorBottomSheet(
                        errorMessage = amError.errorMessage,
                        onCreateAccount = {
                            register()
                            dismissBottomSheet()
                        },
                        editCredentials = ::dismissBottomSheet
                    )
                }

                is AmError.OtherError -> {
                    Timber.d("TEST_ERROR_MESSAGE ${amError.message}")
                    showCustomErrorMessage(errorMessage = amError.errorMessage.orEmpty())
                }

                AmError.Unauthorized -> showCustomErrorMessage(errorMessage = amError.message.orEmpty())

                AmError.ConnectionError -> showConnectionErrorBottomSheet()
                AmError.UnknownError -> {}
            }

            is AmResult.Loading -> startLoading()
            is AmResult.Success -> onSuccess()
        }

    }

}

data class AmAuthData(
    val email: String = "",
    val password: String = "",
) {
    val validateEmail: Boolean get() = email.isValidEmail()
    val validatePassword: Boolean get() = password.isValidPassword()
    val validateData: Boolean get() = validateEmail && validatePassword
}