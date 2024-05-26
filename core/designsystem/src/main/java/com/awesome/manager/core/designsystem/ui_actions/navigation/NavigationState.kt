package com.awesome.manager.core.designsystem.ui_actions.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface MainNavigation {
    val navigationAction: StateFlow<NavigationAction?>
    fun NavigationAction.sendAction()
    fun resetNavigationAction()
    fun navigatePopBack()
    fun navigateToAccountDetails(accountId: String)
    fun navigateToTransactionDetails(transactionId: String)
    fun navigateToCreateAccount()
    fun navigateToCreateTransaction(accountId: String?)
    fun navigateToEditAccount(accountId: String)
    fun navigateToEditTransaction(accountId: String?, transactionId: String)
}

class MainNavigationState : MainNavigation {
    private val _navigationAction: MutableStateFlow<NavigationAction?> = MutableStateFlow(null)
    override val navigationAction: StateFlow<NavigationAction?> = _navigationAction

    override fun NavigationAction.sendAction() = _navigationAction.update { this }
    override fun resetNavigationAction() = _navigationAction.update { null }
    override fun navigatePopBack() = NavigationAction.PopBack.sendAction()

    override fun navigateToAccountDetails(accountId: String) =
        NavigationDestination.AccountDetails(accountId).asNavigation().sendAction()

    override fun navigateToTransactionDetails(transactionId: String) =
        NavigationDestination.TransactionDetails(transactionId).asNavigation().sendAction()

    override fun navigateToCreateAccount() =
        NavigationDestination.AccountEditor(null).asNavigation().sendAction()

    override fun navigateToCreateTransaction(accountId: String?) =
        NavigationDestination.TransactionEditor(accountId = accountId, transactionId = null)
            .asNavigation().sendAction()

    override fun navigateToEditAccount(accountId: String) =
        NavigationDestination.AccountEditor(accountId).asNavigation().sendAction()

    override fun navigateToEditTransaction(accountId: String?, transactionId: String) =
        NavigationDestination.TransactionEditor(
            accountId = accountId,
            transactionId = transactionId
        ).asNavigation().sendAction()

}