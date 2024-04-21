package com.awesome.manager.core.designsystem.ui_actions

import androidx.compose.foundation.lazy.LazyListScope
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

    fun navigateToCreateAccount() =
        NavigationAction.CreateAccount.sendAction()

    fun navigateToAccountDetails(accountId: String) =
        NavigationAction.ReadAccount(accountId).sendAction()

    fun navigateToEditAccount(accountId: String) =
        NavigationAction.EditAccount(accountId).sendAction()

    fun navigateToCreateTransaction(accountId: String?) =
        NavigationAction.CreateTransaction(accountId).sendAction()

    fun navigateToEditTransaction(transactionId: String) =
        NavigationAction.EditTransaction(transactionId).sendAction()

    fun navigateToTransaction(transactionId: String) =
        NavigationAction.ReadTransaction(transactionId).sendAction()


    /////////********* APP_BAR  *********/////////

    private val _appBarAction: MutableStateFlow<AppBarAction> = MutableStateFlow(AppBarAction.Idle)
    val appBarAction: StateFlow<AppBarAction> = _appBarAction

    fun AppBarAction.sendAction() = _appBarAction.update { this }

    fun resetAppBar() = AppBarAction.Idle.sendAction()

    fun showMainAppBar(onAddAccount: (() -> Unit)?, onAddTransaction: (() -> Unit)?) =
        AppBarAction.MainNavigation(
            onAddAccount = onAddAccount, onAddTransaction = onAddTransaction
        ).sendAction()

    fun showSearchAppBar(syncString: (String) -> Unit) =
        AppBarAction.Search(syncString, false).sendAction()

    fun showCreateAppBar(title: String, onCancel: () -> Unit, onSave: () -> Unit) =
        AppBarAction.Create(title = title, onCancel = onCancel, onCreate = onSave).sendAction()

    fun showEditAppBar(title: String, onCancel: () -> Unit, onSave: () -> Unit) =
        AppBarAction.Edit(title, onCancel, onSave).sendAction()

    fun showReadAppBar(
        title: String,
        canEdit: Boolean,
        onBack: () -> Unit,
        onEdit: () -> Unit,
        onAddTransaction: (() -> Unit)?
    ) =
        AppBarAction.Read(
            title = title,
            canEdit = canEdit,
            onBack = onBack,
            onEdit = onEdit,
            onAddTransaction = onAddTransaction
        ).sendAction()


    /////////********* BOTTOM_SHEET  *********/////////

    private val _bottomSheetAction: MutableStateFlow<BottomSheetAction> =
        MutableStateFlow(BottomSheetAction.Empty)
    val bottomSheetAction: StateFlow<BottomSheetAction> = _bottomSheetAction

    fun BottomSheetAction.sendAction() = _bottomSheetAction.update { this }

    fun dismissBottomSheet() = _bottomSheetAction.update { it.asClose() }
    fun resetBottomSheet() = BottomSheetAction.Empty.sendAction()

    fun showProfileBottomSheet(email: String, logout: () -> Unit) =
        BottomSheetAction.Profile(email = email, logout = logout).sendAction()

    fun showSearchForAccountBottomSheet(
        items: LazyListScope.() -> Unit,
        searchKey: (String) -> Unit
    ) =
        BottomSheetAction.SearchForAccount(items = items, onReSearch = searchKey).sendAction()

    fun showAccountCreatedBottomSheet() =
        BottomSheetAction.AccountCreated(dismiss = ::dismissBottomSheet).sendAction()

    fun showPasswordRestedBottomSheet() =
        BottomSheetAction.PasswordRested(dismiss = ::dismissBottomSheet).sendAction()

    fun showUnknownErrorBottomSheet() =
        BottomSheetAction.UnknownError(dismiss = ::dismissBottomSheet).sendAction()

    fun showAuthErrorBottomSheet(
        errorMessage: String, onCreateAccount: () -> Unit, editCredentials: () -> Unit
    ) =
        BottomSheetAction.AuthError(
            errorMessage = errorMessage,
            createNewAccount = onCreateAccount,
            editCredentials = editCredentials
        ).sendAction()

    fun showConnectionErrorBottomSheet() =
        BottomSheetAction.ConnectionError(dismiss = ::dismissBottomSheet).sendAction()

    fun showCustomErrorMessage(errorMessage: String) =
        BottomSheetAction.CustomError(dismiss = ::dismissBottomSheet, errorMessage = errorMessage)
            .sendAction()


    /////////********* OTHER  *********/////////

    private val _otherAction: MutableStateFlow<OtherAction> = MutableStateFlow(OtherAction.Idle)
    val otherAction: StateFlow<OtherAction> = _otherAction
    fun OtherAction.sendAction() {
        _otherAction.update { this }
    }

    fun pickDate(date: () -> String) {
        OtherAction.PickDate(date).sendAction()
    }

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    fun startLoading() = _loading.update { true }
    fun stopLoading() = _loading.update { false }

}