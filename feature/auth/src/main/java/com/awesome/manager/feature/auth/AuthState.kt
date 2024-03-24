package com.awesome.manager.feature.auth

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.common.extentions.isValiedEmail
import com.awesome.manager.core.common.extentions.isValiedPassword
import com.awesome.manager.core.common.results.AmError
import com.awesome.manager.core.common.results.AmResult
import com.awesome.manager.core.designsystem.ui_actions.MainActionsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class AuthScreenState(
    val login: () -> Unit, val register: () -> Unit, val resetPassword: () -> Unit,
) : MainActionsState() {

    private val _authData: MutableStateFlow<AmAuthData> = MutableStateFlow(AmAuthData())
    val authData: StateFlow<AmAuthData> = _authData
    fun updateEmail(email: String) = _authData.update { it.copy(email = email) }
    fun updatePassword(password: String) = _authData.update { it.copy(password = password) }

    fun updateStateBasedOnResult(
        amResult: AmResult<Any>, onSuccess: () -> Unit,
    ) {
        Timber.d("TEST_AUTH STATE $amResult")
        stopLoading()
        when (amResult) {
            is AmResult.Error -> when (val amError = amResult.amError) {

                is AmError.BadRequest -> showAuthErrorBottomSheet()

                is AmError.OtherError -> showCustomErrorMessage(errorMessage = amError.message.orEmpty())

                AmError.Unauthorized -> showCustomErrorMessage(errorMessage = amError.message.orEmpty())

                AmError.ConnectionError -> showConnectionErrorBottomSheet()

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
    val validateEmail: Boolean get() = email.isValiedEmail()
    val validatePassword: Boolean get() = password.isValiedPassword()
    val validateData: Boolean get() = validateEmail && validatePassword
}