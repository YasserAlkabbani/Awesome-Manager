package com.awesome.manager.core.designsystem.ui_actions.bottomsheet

import androidx.compose.foundation.lazy.LazyListScope
import com.awesome.manager.core.designsystem.ui_actions.main.MainAction

sealed class BottomSheetAction(
    open val isDismissible: Boolean = false
) {

    data class Idle<T : BottomSheetAction>(val bottomSheet: T?) : BottomSheetAction()

    data class Dismiss<T : BottomSheetAction>(val bottomSheet: T) : BottomSheetAction()

    data class Profile(
        val email: String,
        val logout: () -> Unit
    ) : BottomSheetAction()

    data class SearchForAccount(
        val items: LazyListScope.() -> Unit,
        val onReSearch: (String) -> Unit
    ) : BottomSheetAction()

    data class AccountCreated(val dismiss: () -> Unit) :
        BottomSheetAction()

    data class PasswordRested(val dismiss: () -> Unit) :
        BottomSheetAction()

    data class AuthError(
        val errorMessage: String, val createNewAccount: () -> Unit, val editCredentials: () -> Unit
    ) : BottomSheetAction()

    data class UnknownError(val dismiss: () -> Unit) :
        BottomSheetAction()

    data class ConnectionError(val dismiss: () -> Unit) :
        BottomSheetAction()

    data class CustomError(
        val errorMessage: String, val dismiss: () -> Unit
    ) : BottomSheetAction()

    fun isEmpty() = this is Idle<*> && bottomSheet == null

    fun sendMainAction(sendMainAction: (MainAction) -> Unit, resetBottomSheet: () -> Unit) {
        when (this) {
            is Idle<*> -> {}
            else -> {
                resetBottomSheet()
                sendMainAction(MainAction.BottomSheet(this))
            }
        }
    }

}