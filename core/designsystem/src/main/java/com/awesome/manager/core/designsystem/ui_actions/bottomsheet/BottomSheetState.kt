package com.awesome.manager.core.designsystem.ui_actions.bottomsheet

import androidx.compose.foundation.lazy.LazyListScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface MainBottomSheet {
    fun idleBottomSheet(clearBottomSheet: Boolean = false)
    fun showProfileBottomSheet(email: String, logout: () -> Unit)
    fun showSearchForAccountBottomSheet(
        items: LazyListScope.() -> Unit,
        searchKey: (String) -> Unit
    )

    fun showAccountCreatedBottomSheet()
    fun showPasswordRestedBottomSheet()
    fun showUnknownErrorBottomSheet()
    fun showAuthErrorBottomSheet(
        errorMessage: String,
        onCreateAccount: () -> Unit,
        editCredentials: () -> Unit
    )

    fun showConnectionErrorBottomSheet()
    fun showCustomErrorMessage(errorMessage: String)
    val bottomSheetAction: StateFlow<BottomSheetAction>
    fun dismissBottomSheet()
    fun BottomSheetAction.sendAction()
}

class MainBottomSheetState : MainBottomSheet {
    private val _bottomSheetAction: MutableStateFlow<BottomSheetAction> =
        MutableStateFlow(BottomSheetAction.Idle(null))
    override val bottomSheetAction: StateFlow<BottomSheetAction> = _bottomSheetAction

    override fun BottomSheetAction.sendAction() = _bottomSheetAction.update { this }

    override fun dismissBottomSheet() = _bottomSheetAction.update {
        if (it is BottomSheetAction.Idle<*>)
            it.bottomSheet?.let { BottomSheetAction.Dismiss(it) } ?: BottomSheetAction.Idle(null)
        else BottomSheetAction.Dismiss(it)
    }

    override fun idleBottomSheet(clearBottomSheet: Boolean) = _bottomSheetAction.update {
        when (it) {
            is BottomSheetAction.Idle<*> -> it
            is BottomSheetAction.Dismiss<*> -> BottomSheetAction.Idle(if (clearBottomSheet) null else it)
            else -> BottomSheetAction.Idle(it)
        }
    }

    override fun showProfileBottomSheet(email: String, logout: () -> Unit) =
        BottomSheetAction.Profile(email = email, logout = logout).sendAction()

    override fun showSearchForAccountBottomSheet(
        items: LazyListScope.() -> Unit,
        searchKey: (String) -> Unit
    ) =
        BottomSheetAction.SearchForAccount(items = items, onReSearch = searchKey).sendAction()

    override fun showAccountCreatedBottomSheet() =
        BottomSheetAction.AccountCreated(dismiss = ::dismissBottomSheet).sendAction()

    override fun showPasswordRestedBottomSheet() =
        BottomSheetAction.PasswordRested(dismiss = ::dismissBottomSheet).sendAction()

    override fun showUnknownErrorBottomSheet() =
        BottomSheetAction.UnknownError(dismiss = ::dismissBottomSheet).sendAction()

    override fun showAuthErrorBottomSheet(
        errorMessage: String, onCreateAccount: () -> Unit, editCredentials: () -> Unit
    ) =
        BottomSheetAction.AuthError(
            errorMessage = errorMessage,
            createNewAccount = onCreateAccount,
            editCredentials = editCredentials
        ).sendAction()

    override fun showConnectionErrorBottomSheet() =
        BottomSheetAction.ConnectionError(dismiss = ::dismissBottomSheet).sendAction()

    override fun showCustomErrorMessage(errorMessage: String) =
        BottomSheetAction.CustomError(dismiss = ::dismissBottomSheet, errorMessage = errorMessage)
            .sendAction()
}
