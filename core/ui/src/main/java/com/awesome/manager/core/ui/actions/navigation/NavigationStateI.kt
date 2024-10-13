package com.awesome.manager.core.ui.actions.navigation

import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.ui.actions.main.NavigationAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


interface NavigationState {

    fun NavigationAction.applyAction()

    fun navigatePopBack() = NavigationAction.NavigateUp.applyAction()

    fun navigateToAccountDetails(accountId: String) =
        NavigationAction.AccountDetails(accountId).applyAction()

    fun navigateToTransactionDetails(transactionId: String) =
        NavigationAction.TransactionDetails(transactionId).applyAction()

    fun navigateToCreateAccount() =
        NavigationAction.AccountEditor(null).applyAction()

    fun navigateToCreateTransaction(accountId: String?) =
        NavigationAction.TransactionEditor(accountId = accountId, transactionId = null)
            .applyAction()

    fun navigateToEditAccount(accountId: String) =
        NavigationAction.AccountEditor(accountId).applyAction()

    fun navigateToEditTransaction(accountId: String?, transactionId: String) =
        NavigationAction.TransactionEditor(
            accountId = accountId,
            transactionId = transactionId
        ).applyAction()

}