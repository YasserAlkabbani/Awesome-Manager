package com.awesome.manager.core.designsystem.actions.bottomsheet

import androidx.compose.foundation.lazy.LazyListScope
import com.awesome.manager.core.designsystem.actions.main.BottomSheetAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface BottomSheetStateI {
    val bottomSheetAction: StateFlow<BottomSheetAction?>
    fun BottomSheetAction.applyAction()
    fun doneBottomSheetAction()
    fun dismissBottomSheet()

    fun showProfileBottomSheet(email: String, logout: () -> Unit)
    fun showSearchForAccountBottomSheet(
        items: LazyListScope.() -> Unit,
        searchKey: (String) -> Unit
    )

    fun showAccountCreatedBottomSheet()
    fun showPasswordRestedBottomSheet()
    fun showUnknownErrorBottomSheet()
    fun showAuthErrorBottomSheet(
        errorMessage: String, onCreateAccount: () -> Unit, editCredentials: () -> Unit
    )

    fun showConnectionErrorBottomSheet()
    fun showCustomErrorMessage(errorMessage: String)

}

class BottomSheetState : BottomSheetStateI {

    private val _bottomSheetAction: MutableStateFlow<BottomSheetAction?> = MutableStateFlow(null)
    override val bottomSheetAction: StateFlow<BottomSheetAction?> = _bottomSheetAction

    override fun BottomSheetAction.applyAction() = _bottomSheetAction.update { this }
    override fun doneBottomSheetAction() = _bottomSheetAction.update { null }

    override fun dismissBottomSheet() = _bottomSheetAction.update {
        BottomSheetAction.Dismiss(it)
    }

    override fun showProfileBottomSheet(email: String, logout: () -> Unit) =
        BottomSheetAction.Profile(email = email, logout = logout).applyAction()

    override fun showSearchForAccountBottomSheet(
        items: LazyListScope.() -> Unit,
        searchKey: (String) -> Unit
    ) =
        BottomSheetAction.SearchForAccount(items = items, onReSearch = searchKey).applyAction()

    override fun showAccountCreatedBottomSheet() =
        BottomSheetAction.AccountCreated(dismiss = ::dismissBottomSheet).applyAction()

    override fun showPasswordRestedBottomSheet() =
        BottomSheetAction.PasswordRested(dismiss = ::dismissBottomSheet).applyAction()

    override fun showUnknownErrorBottomSheet() =
        BottomSheetAction.UnknownError(dismiss = ::dismissBottomSheet).applyAction()

    override fun showAuthErrorBottomSheet(
        errorMessage: String, onCreateAccount: () -> Unit, editCredentials: () -> Unit
    ) =
        BottomSheetAction.AuthError(
            errorMessage = errorMessage,
            createNewAccount = onCreateAccount,
            editCredentials = editCredentials
        ).applyAction()

    override fun showConnectionErrorBottomSheet() =
        BottomSheetAction.ConnectionError(dismiss = ::dismissBottomSheet).applyAction()

    override fun showCustomErrorMessage(errorMessage: String) =
        BottomSheetAction.CustomError(dismiss = ::dismissBottomSheet, errorMessage = errorMessage)
            .applyAction()
}

fun BottomSheetAction?.sendMainAction(
    sendMainAction: (MainAction) -> Unit, doneBottomSheetAction: () -> Unit
) {
    this?.let {
        doneBottomSheetAction()
        sendMainAction(this)
    }
}

