package com.awesome.manager.core.designsystem.actions.navigation

import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.main.NavigationAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


interface NavigationState {

    fun NavigationAction.applyAction()

    fun navigatePopBack() = NavigationAction.NavigateUp.applyAction()

    fun navigateToAccountDetails(accountId: String) =
        NavigationDestination.AccountDetails(accountId).asNavigation().applyAction()

    fun navigateToTransactionDetails(transactionId: String) =
        NavigationDestination.TransactionDetails(transactionId).asNavigation().applyAction()

    fun navigateToCreateAccount() =
        NavigationDestination.AccountEditor(null).asNavigation().applyAction()

    fun navigateToCreateTransaction(accountId: String?) =
        NavigationDestination.TransactionEditor(accountId = accountId, transactionId = null)
            .asNavigation().applyAction()

    fun navigateToEditAccount(accountId: String) =
        NavigationDestination.AccountEditor(accountId).asNavigation().applyAction()

    fun navigateToEditTransaction(accountId: String?, transactionId: String) =
        NavigationDestination.TransactionEditor(
            accountId = accountId,
            transactionId = transactionId
        ).asNavigation().applyAction()

}