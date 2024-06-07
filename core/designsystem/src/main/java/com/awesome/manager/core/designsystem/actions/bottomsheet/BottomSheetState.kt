package com.awesome.manager.core.designsystem.actions.bottomsheet

import androidx.compose.foundation.lazy.LazyListScope
import com.awesome.manager.core.designsystem.actions.main.BottomSheetAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun showPickRangeDateBottomSheet(
        initTime: Long,
        setDate: (Long, Long) -> Unit,
        dismiss: () -> Unit
    )

    fun showPickDateBottomSheet(initTime: Long, setDate: (Long) -> Unit, dismiss: () -> Unit)

}

class BottomSheetState : BottomSheetStateI {

    private val _bottomSheetAction: MutableStateFlow<BottomSheetAction?> = MutableStateFlow(null)
    override val bottomSheetAction: StateFlow<BottomSheetAction?> = _bottomSheetAction.asStateFlow()

    override fun BottomSheetAction.applyAction() = _bottomSheetAction.update { this }
    private fun BottomSheetContent.open() = BottomSheetAction.Open(this).applyAction()


    override fun doneBottomSheetAction() = _bottomSheetAction.update { null }

    override fun dismissBottomSheet() = _bottomSheetAction.update {
        (it as? BottomSheetAction.Open).let { BottomSheetAction.Dismiss(it?.content) }
    }

    override fun showProfileBottomSheet(email: String, logout: () -> Unit) =
        BottomSheetContent.Profile(email = email, logout = logout).open()

    override fun showSearchForAccountBottomSheet(
        items: LazyListScope.() -> Unit,
        searchKey: (String) -> Unit
    ) =
        BottomSheetContent.SearchForAccount(items = items, onReSearch = searchKey).open()

    override fun showAccountCreatedBottomSheet() =
        BottomSheetContent.AccountCreated(dismiss = ::dismissBottomSheet).open()

    override fun showPasswordRestedBottomSheet() =
        BottomSheetContent.PasswordRested(dismiss = ::dismissBottomSheet).open()

    override fun showUnknownErrorBottomSheet() =
        BottomSheetContent.UnknownError(dismiss = ::dismissBottomSheet).open()

    override fun showAuthErrorBottomSheet(
        errorMessage: String, onCreateAccount: () -> Unit, editCredentials: () -> Unit
    ) =
        BottomSheetContent.AuthError(
            errorMessage = errorMessage,
            createNewAccount = onCreateAccount,
            editCredentials = editCredentials
        ).open()

    override fun showConnectionErrorBottomSheet() =
        BottomSheetContent.ConnectionError(dismiss = ::dismissBottomSheet).open()

    override fun showCustomErrorMessage(errorMessage: String) =
        BottomSheetContent.CustomError(dismiss = ::dismissBottomSheet, errorMessage = errorMessage)
            .open()

    override fun showPickDateBottomSheet(
        initTime: Long, setDate: (Long) -> Unit, dismiss: () -> Unit
    ) =
        BottomSheetContent.PickDate(initTime = initTime, setDate = setDate, dismiss = dismiss)
            .open()

    override fun showPickRangeDateBottomSheet(
        initTime: Long, setDate: (Long, Long) -> Unit, dismiss: () -> Unit
    ) =
        BottomSheetContent.PickRangeDate(initTime = initTime, setDate = setDate, dismiss = dismiss)
            .open()

}

fun BottomSheetAction?.sendMainAction(
    sendMainAction: (MainAction) -> Unit, doneBottomSheetAction: () -> Unit
) {
    this?.let {
        doneBottomSheetAction()
        sendMainAction(this)
    }
}

