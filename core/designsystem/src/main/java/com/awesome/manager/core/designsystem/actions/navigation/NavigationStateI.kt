package com.awesome.manager.core.designsystem.actions.navigation

import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.main.NavigationAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


interface NavigationStateI {
    val navigationAction: StateFlow<NavigationAction?>
    fun NavigationAction.applyAction()
    fun doneNavigationAction()
    fun navigatePopBack()
    fun navigateToAccountDetails(accountId: String)
    fun navigateToTransactionDetails(transactionId: String)
    fun navigateToCreateAccount()
    fun navigateToCreateTransaction(accountId: String? = null)
    fun navigateToEditAccount(accountId: String)
    fun navigateToEditTransaction(accountId: String?, transactionId: String)
}

class NavigationState : NavigationStateI {

    private val _navigationAction: MutableStateFlow<NavigationAction?> = MutableStateFlow(null)
    override val navigationAction: StateFlow<NavigationAction?> = _navigationAction.asStateFlow()

    override fun NavigationAction.applyAction() = _navigationAction.update { this }
    override fun doneNavigationAction() = _navigationAction.update { null }

    override fun navigatePopBack() = NavigationAction.PopBack.applyAction()

    override fun navigateToAccountDetails(accountId: String) =
        NavigationDestination.AccountDetails(accountId).asNavigation().applyAction()

    override fun navigateToTransactionDetails(transactionId: String) =
        NavigationDestination.TransactionDetails(transactionId).asNavigation().applyAction()

    override fun navigateToCreateAccount() =
        NavigationDestination.AccountEditor(null).asNavigation().applyAction()

    override fun navigateToCreateTransaction(accountId: String?) =
        NavigationDestination.TransactionEditor(accountId = accountId, transactionId = null)
            .asNavigation().applyAction()

    override fun navigateToEditAccount(accountId: String) =
        NavigationDestination.AccountEditor(accountId).asNavigation().applyAction()

    override fun navigateToEditTransaction(accountId: String?, transactionId: String) =
        NavigationDestination.TransactionEditor(
            accountId = accountId,
            transactionId = transactionId
        ).asNavigation().applyAction()

}

fun NavigationAction?.sendMainAction(
    sendMainAction: (MainAction) -> Unit, doneNavigationAction: () -> Unit
) {
    this?.let {
        doneNavigationAction()
        sendMainAction(this)
    }
}