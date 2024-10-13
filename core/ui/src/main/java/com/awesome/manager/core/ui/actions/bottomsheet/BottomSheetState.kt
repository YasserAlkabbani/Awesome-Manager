package com.awesome.manager.core.ui.actions.bottomsheet

import com.awesome.manager.core.ui.actions.main.BottomSheetAction

interface BottomSheetState {


    fun BottomSheetAction.applyAction()

    fun dismissBottomSheet() = BottomSheetAction.Dismiss.applyAction()

    fun showProfileBottomSheet(email: String, logout: () -> Unit) =
        BottomSheetAction.Profile(email = email, logout = logout).applyAction()

//    fun showSearchWithContentBottomSheet(
//        searchLabel: String, initSearch: String, onReSearch: (String) -> Unit,
//        onSearchDone: () -> Unit, content: @Composable () -> Unit,
//    ) = BottomSheetContent.SearchWithContent(
//        searchLabel = searchLabel, initSearch = initSearch,
//        onReSearch = onReSearch, onSearchDone, content = content
//    ).showBottomSheet(isDismissible = true)

    fun showAccountCreatedBottomSheet() =
        BottomSheetAction.AccountCreated(dismiss = ::dismissBottomSheet)
            .applyAction()

    fun showPasswordRestedBottomSheet() =
        BottomSheetAction.PasswordRested(dismiss = ::dismissBottomSheet)
            .applyAction()

    fun showUnknownErrorBottomSheet() =
        BottomSheetAction.UnknownError(dismiss = ::dismissBottomSheet)
            .applyAction()

    fun showAuthErrorBottomSheet(
        errorMessage: String, onCreateAccount: () -> Unit, editCredentials: () -> Unit
    ) =
        BottomSheetAction.AuthError(
            errorMessage = errorMessage, createNewAccount = onCreateAccount,
            editCredentials = editCredentials
        ).applyAction()

    fun showConnectionErrorBottomSheet() =
        BottomSheetAction.ConnectionError(dismiss = ::dismissBottomSheet)
            .applyAction()

    fun showCustomErrorMessage(errorMessage: String) =
        BottomSheetAction.CustomError(dismiss = ::dismissBottomSheet, errorMessage = errorMessage)
            .applyAction()

    fun showPickDateBottomSheet(
        initTime: Long, setDate: (Long) -> Unit, dismiss: () -> Unit
    ) =
        BottomSheetAction.PickDate(initTime = initTime, setDate = setDate, dismiss = dismiss)
            .applyAction()

    fun showPickRangeDateBottomSheet(
        initTime: Long, setDate: (Long, Long) -> Unit, dismiss: () -> Unit
    ) =
        BottomSheetAction.PickRangeDate(initTime = initTime, setDate = setDate, dismiss = dismiss)
            .applyAction()

}
