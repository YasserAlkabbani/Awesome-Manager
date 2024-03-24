package com.awesome.manager.core.designsystem.ui_actions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class MainActionsState {

    /////////********* NAVIGATION  *********/////////

    private val _navigationAction: MutableStateFlow<NavigationAction> =
        MutableStateFlow(NavigationAction.Idle)
    val navigationAction: StateFlow<NavigationAction> = _navigationAction

    fun NavigationAction.sendAction() = _navigationAction.update { this }
    fun resetNavigationAction() = NavigationAction.Idle.sendAction()
    fun navigatePopBack() = NavigationAction.PopBack.sendAction()

    fun navigateToAccount(accountId: String) =
        NavigationAction.ReadAccount(accountId).sendAction()

    fun navigateToAccountDetails(accountId: String) =
        NavigationAction.ReadAccount(accountId).sendAction()

    fun navigateToEditAccount(accountId: String) =
        NavigationAction.EditAccount(accountId).sendAction()

    fun navigateToCreateTransaction(accountId: String) =
        NavigationAction.CreateTransaction(accountId).sendAction()

    fun navigateToEditTransaction(transactionId: String) =
        NavigationAction.EditTransaction(transactionId).sendAction()

    fun navigateToTransaction(transactionId: String) =
        NavigationAction.ReadTransaction(transactionId).sendAction()


    /////////********* APP_BAR  *********/////////

    private val _appBarAction: MutableStateFlow<AppBarAction> = MutableStateFlow(AppBarAction.Idle)
    val appBarAction: StateFlow<AppBarAction> = _appBarAction

    fun AppBarAction.sendAction() = _appBarAction.update { this }

    fun showSearchAppBar(syncString: (String) -> Unit) =
        AppBarAction.Search(syncString, false).sendAction()

    fun showCreateAppBar(title: String, onCancel: () -> Unit, onSave: () -> Unit) =
        AppBarAction.Create(title = title, onCancel = onCancel, onSave = onSave)

    fun showEditAppBar(title: String, onCancel: () -> Unit, onSave: () -> Unit) =
        AppBarAction.Edit(title, onCancel, onSave)

    fun showReadAppBar(title: String, onBack: () -> Unit, onEdit: () -> Unit) =
        AppBarAction.Read(title, onBack, onEdit)


    /////////********* BOTTOM_SHEET  *********/////////

    private val _bottomSheetState: MutableStateFlow<BottomSheetAction> =
        MutableStateFlow(BottomSheetAction.Empty)
    val bottomSheetAction: StateFlow<BottomSheetAction> = _bottomSheetState

    fun BottomSheetAction.sendAction() = _bottomSheetState.update { this }

    fun dismissBottomSheet() = _bottomSheetState.update { it.asClose() }
    fun resetBottomSheet() = BottomSheetAction.Empty.sendAction()

    fun showProfileBottomSheet(email: String, logout: () -> Unit) =
        BottomSheetAction.Profile(email = email, logout = logout).sendAction()

    fun showSearchForAccountBottomSheet() =
        BottomSheetAction.SearchForAccount().sendAction()

    fun showAccountCreatedBottomSheet() =
        BottomSheetAction.AccountCreated(dismiss = ::dismissBottomSheet).sendAction()

    fun showPasswordRestedBottomSheet() =
        BottomSheetAction.PasswordRested(dismiss = ::dismissBottomSheet).sendAction()

    fun showUnknownErrorBottomSheet() =
        BottomSheetAction.UnknownError(dismiss = ::dismissBottomSheet).sendAction()

    fun showAuthErrorBottomSheet() =
        BottomSheetAction.AuthError(dismiss = ::dismissBottomSheet).sendAction()

    fun showConnectionErrorBottomSheet() =
        BottomSheetAction.ConnectionError(dismiss = ::dismissBottomSheet).sendAction()

    fun showCustomErrorMessage(errorMessage: String) =
        BottomSheetAction.CustomError(dismiss = ::dismissBottomSheet, errorMessage = errorMessage)
            .sendAction()


    /////////********* OTHER  *********/////////

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    fun startLoading() = _loading.update { true }
    fun stopLoading() = _loading.update { false }

}