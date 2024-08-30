package com.awesome.manager.core.designsystem.actions.bottomsheet

import androidx.compose.runtime.Composable
import com.awesome.manager.core.designsystem.actions.main.BottomSheetAction

interface BottomSheetState {


    fun BottomSheetAction.applyAction()

    private fun BottomSheetContent.showBottomSheet(isDismissible: Boolean) =
        BottomSheetAction.Open(content = this, isDismissible = isDismissible).applyAction()

    fun dismissBottomSheet() = BottomSheetAction.Dismiss.applyAction()

    fun showProfileBottomSheet(email: String, logout: () -> Unit) =
        BottomSheetContent.Profile(email = email, logout = logout)
            .showBottomSheet(isDismissible = true)

    fun showSearchWithContentBottomSheet(
        searchLabel: String, initSearch: String, onReSearch: (String) -> Unit,
        onSearchDone: () -> Unit, content: @Composable () -> Unit,
    ) = BottomSheetContent.SearchWithContent(
        searchLabel = searchLabel, initSearch = initSearch,
        onReSearch = onReSearch, onSearchDone, content = content
    ).showBottomSheet(isDismissible = true)

    fun showAccountCreatedBottomSheet() =
        BottomSheetContent.AccountCreated(dismiss = ::dismissBottomSheet)
            .showBottomSheet(isDismissible = true)

    fun showPasswordRestedBottomSheet() =
        BottomSheetContent.PasswordRested(dismiss = ::dismissBottomSheet)
            .showBottomSheet(isDismissible = true)

    fun showUnknownErrorBottomSheet() =
        BottomSheetContent.UnknownError(dismiss = ::dismissBottomSheet)
            .showBottomSheet(isDismissible = true)

    fun showAuthErrorBottomSheet(
        errorMessage: String, onCreateAccount: () -> Unit, editCredentials: () -> Unit
    ) =
        BottomSheetContent.AuthError(
            errorMessage = errorMessage, createNewAccount = onCreateAccount,
            editCredentials = editCredentials
        ).showBottomSheet(isDismissible = true)

    fun showConnectionErrorBottomSheet() =
        BottomSheetContent.ConnectionError(dismiss = ::dismissBottomSheet)
            .showBottomSheet(isDismissible = true)

    fun showCustomErrorMessage(errorMessage: String) =
        BottomSheetContent.CustomError(dismiss = ::dismissBottomSheet, errorMessage = errorMessage)
            .showBottomSheet(isDismissible = true)

    fun showPickDateBottomSheet(
        initTime: Long, setDate: (Long) -> Unit, dismiss: () -> Unit
    ) =
        BottomSheetContent.PickDate(initTime = initTime, setDate = setDate, dismiss = dismiss)
            .showBottomSheet(isDismissible = true)

    fun showPickRangeDateBottomSheet(
        initTime: Long, setDate: (Long, Long) -> Unit, dismiss: () -> Unit
    ) =
        BottomSheetContent.PickRangeDate(initTime = initTime, setDate = setDate, dismiss = dismiss)
            .showBottomSheet(isDismissible = true)

}
