package com.awesome.manager.core.designsystem.actions.appbar

import com.awesome.manager.core.designsystem.actions.main.AppBarAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppBarButton(
    val text: String,
    val click: () -> Unit,
    val errorMessage: String? = null,
) {
    val showButton: Boolean = errorMessage == null
}

interface AppBarStateI {

    val appBarAction: StateFlow<AppBarAction?>
    fun AppBarAction.applyAction()
    fun doneAppBarAction()

    fun setForAuth(isLoading: Boolean, loginButton: AppBarButton)

    fun setForHomeScreen()

    fun setForAccountsScreen()

    fun setForTransactionsScreen()

    fun setForAccountDetailsScreen(
        backButton: Boolean, onEditButton: AppBarButton?,
        transactionEditor: NavigationDestination.TransactionEditor?
    )

    fun setForEditAccountScreen(
        cancelButton: Boolean, saveButton: AppBarButton
    )

    fun setForTransactionDetailsScreen(
        backButton: Boolean, editButton: AppBarButton?
    )

    fun setForEditTransactionScreen(
        cancelButton: Boolean, saveButton: AppBarButton?
    )

}

class AppBarState : AppBarStateI {

    private val _appBarAction: MutableStateFlow<AppBarAction?> = MutableStateFlow(null)
    override val appBarAction: StateFlow<AppBarAction?> = _appBarAction.asStateFlow()

    override fun AppBarAction.applyAction() = _appBarAction.update { this }
    override fun doneAppBarAction() = _appBarAction.update { null }

    private fun updateAppBarData(
        visible: Boolean = true, isLoading: Boolean = false,
        bottomNavigation: Boolean = false,
        accountEditor: NavigationDestination.AccountEditor? = null,
        transactionEditor: NavigationDestination.TransactionEditor? = null,
        backButton: Boolean = false, cancelButton: Boolean = false, moreButton: Boolean = false,
        appBarButton: AppBarButton? = null
    ) = _appBarAction.update {
        AppBarAction(
            navigateToAccountEditor = accountEditor,
            navigateToTransactionEditor = transactionEditor,
            bottomNavigation = bottomNavigation,
            visible = visible, isLoading = isLoading,
            backButton = backButton, cancelButton = cancelButton,
            moreButton = moreButton, appBarButton = appBarButton
        )
    }

    override fun setForAuth(isLoading: Boolean, loginButton: AppBarButton) = updateAppBarData(
        isLoading = isLoading, appBarButton = loginButton
    )

    override fun setForHomeScreen() = updateAppBarData(bottomNavigation = true, moreButton = true)

    override fun setForAccountsScreen() = updateAppBarData(
        bottomNavigation = true,
        accountEditor = NavigationDestination.AccountEditor(accountId = null)
    )

    override fun setForTransactionsScreen() = updateAppBarData(
        bottomNavigation = true,
        transactionEditor = NavigationDestination.TransactionEditor(
            accountId = null, transactionId = null
        )
    )

    override fun setForAccountDetailsScreen(
        backButton: Boolean, onEditButton: AppBarButton?,
        transactionEditor: NavigationDestination.TransactionEditor?
    ) = updateAppBarData(
        backButton = backButton, appBarButton = onEditButton,
        transactionEditor = transactionEditor,
    )

    override fun setForEditAccountScreen(
        cancelButton: Boolean, saveButton: AppBarButton
    ) = updateAppBarData(cancelButton = cancelButton, appBarButton = saveButton)

    override fun setForTransactionDetailsScreen(
        backButton: Boolean, editButton: AppBarButton?
    ) = updateAppBarData(backButton = backButton, appBarButton = editButton)

    override fun setForEditTransactionScreen(
        cancelButton: Boolean, saveButton: AppBarButton?
    ) = updateAppBarData(
        cancelButton = cancelButton, appBarButton = saveButton
    )

}

fun AppBarAction?.sendMainAction(
    sendMainAction: (MainAction) -> Unit,
    doneAppBarAction: () -> Unit
) {
    this?.let {
        doneAppBarAction()
        sendMainAction(this)
    }

}