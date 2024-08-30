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

interface AppBarState {

    fun AppBarAction.applyAction()

    private fun updateAppBarData(
        visible: Boolean = true, isLoading: Boolean = false,
        bottomNavigation: Boolean = false,
        accountEditor: NavigationDestination.AccountEditor? = null,
        transactionEditor: NavigationDestination.TransactionEditor? = null,
        backButton: Boolean = false, cancelButton: Boolean = false, moreButton: Boolean = false,
        appBarButton: AppBarButton? = null
    ) = AppBarAction(
        navigateToAccountEditor = accountEditor,
        navigateToTransactionEditor = transactionEditor,
        bottomNavigation = bottomNavigation,
        visible = visible, isLoading = isLoading,
        backButton = backButton, cancelButton = cancelButton,
        moreButton = moreButton, appBarButton = appBarButton
    ).applyAction()

    fun setForAuth(isLoading: Boolean, loginButton: AppBarButton) = updateAppBarData(
        isLoading = isLoading, appBarButton = loginButton
    )

    fun setForHomeScreen() = updateAppBarData(bottomNavigation = true, moreButton = true)

    fun setForAccountsScreen() = updateAppBarData(
        bottomNavigation = true,
        accountEditor = NavigationDestination.AccountEditor(accountId = null)
    )

    fun setForTransactionsScreen() = updateAppBarData(
        bottomNavigation = true,
        transactionEditor = NavigationDestination.TransactionEditor(
            accountId = null, transactionId = null
        )
    )

    fun setForAccountDetailsScreen(
        backButton: Boolean, onEditButton: AppBarButton?,
        transactionEditor: NavigationDestination.TransactionEditor?
    ) = updateAppBarData(
        backButton = backButton, appBarButton = onEditButton,
        transactionEditor = transactionEditor,
    )

    fun setForEditAccountScreen(
        cancelButton: Boolean, saveButton: AppBarButton
    ) = updateAppBarData(cancelButton = cancelButton, appBarButton = saveButton)

    fun setForTransactionDetailsScreen(
        backButton: Boolean, editButton: AppBarButton?
    ) = updateAppBarData(backButton = backButton, appBarButton = editButton)

    fun setForEditTransactionScreen(
        cancelButton: Boolean, saveButton: AppBarButton?
    ) = updateAppBarData(
        cancelButton = cancelButton, appBarButton = saveButton
    )

}