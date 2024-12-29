package com.awesome.manager.core.ui.actions.bottomsheet

import com.awesome.manager.core.ui.actions.main.BottomSheetAction

interface BottomSheetState {


    fun BottomSheetAction.applyAction()

    fun showAccountCreatedBottomSheet() = BottomSheetAction
        .AccountCreated
        .applyAction()

    fun showPasswordRestedBottomSheet() =
        BottomSheetAction.PasswordRested.applyAction()

    fun showUnknownErrorBottomSheet() =
        BottomSheetAction.UnknownError.applyAction()

    fun showConnectionErrorBottomSheet() =
        BottomSheetAction.ConnectionError.applyAction()

    fun showProfileBottomSheet(email: String, logout: () -> Unit) =
        BottomSheetAction.Profile(email = email, logout = logout).applyAction()

    fun showCustomErrorMessage(errorMessage: String) =
        BottomSheetAction.CustomError(errorMessage = errorMessage).applyAction()

    fun showSearchForAccount(initSearch: String, onSelectAccount: (String) -> Unit) =
        BottomSheetAction.SearchForAccount(
            initSearch = initSearch,
            onSelectAccount = onSelectAccount,
        ).applyAction()

    fun showPickDateBottomSheet(
        initTime: Long, setDate: (Long) -> Unit
    ) = BottomSheetAction.PickDate(
        initTime = initTime,
        setDate = setDate,
    ).applyAction()

    fun showPickRangeDateBottomSheet(
        initTime: Long,
        setDate: (Long, Long) -> Unit,
        dismiss: () -> Unit
    ) = BottomSheetAction.PickRangeDate(
        initTime = initTime,
        setDate = setDate,
    ).applyAction()

    fun showAuthErrorBottomSheet(
        errorMessage: String,
        onCreateAccount: () -> Unit,
        editCredentials: () -> Unit
    ) = BottomSheetAction.AuthError(
        errorMessage = errorMessage,
        createNewAccount = onCreateAccount,
        editCredentials = editCredentials
    ).applyAction()


}
