package com.awesome.manager.core.designsystem.ui_actions

sealed class BottomSheetAction(
    open val isOpen: Boolean = false,
    open val isDismissible: Boolean = false
) {

    fun asClose() = when (this) {
        Empty -> this
        is AuthError -> this.copy(isOpen = false)
        is Profile -> this.copy(isOpen = false)
        is AccountCreated -> this.copy(isOpen = false)
        is PasswordRested -> this.copy(isOpen = false)
        is SearchForAccount -> this.copy(isOpen = false)
        is UnknownError -> this.copy(isOpen = false)
        is ConnectionError -> this.copy(isOpen = false)
        is CustomError -> this.copy(isOpen = false)
    }

    data object Empty : BottomSheetAction()

    data class Profile(
        override val isOpen: Boolean = true,
        val email: String,
        val logout: () -> Unit
    ) : BottomSheetAction()

    data class SearchForAccount(override val isOpen: Boolean = true) : BottomSheetAction()

    data class AccountCreated(override val isOpen: Boolean = true, val dismiss: () -> Unit) :
        BottomSheetAction()

    data class PasswordRested(override val isOpen: Boolean = true, val dismiss: () -> Unit) :
        BottomSheetAction()

    data class AuthError(override val isOpen: Boolean = true, val dismiss: () -> Unit) :
        BottomSheetAction()

    data class UnknownError(override val isOpen: Boolean = true, val dismiss: () -> Unit) :
        BottomSheetAction()

    data class ConnectionError(override val isOpen: Boolean = true, val dismiss: () -> Unit) :
        BottomSheetAction()

    data class CustomError(
        override val isOpen: Boolean = true,
        val errorMessage: String,
        val dismiss: () -> Unit
    ) : BottomSheetAction()

    fun sendAction(sendMainAction: (MainActions) -> Unit) {
        sendMainAction(MainActions.BottomSheet(this))
    }

}