package com.awesome.manager.core.ui.actions.navigation

import com.awesome.manager.core.ui.actions.main.NavigationAction


interface NavigationState {

    fun NavigationAction.applyAction()

    fun navigatePopBack() = NavigationAction.NavigateUp.applyAction()

    fun navigateToAccountDetails(accountID: String) =
        NavigationAction.AccountDetails(accountID)
            .applyAction()

    fun navigateToTransactionDetails(accountID: String, transactionID: String) =
        NavigationAction.TransactionDetails(accountID = accountID, transactionID = transactionID)
            .applyAction()

    fun navigateToCreateAccount() =
        NavigationAction.AccountEditor(null)
            .applyAction()

    fun navigateToCreateTransaction(accountId: String?) =
        NavigationAction.TransactionEditor(accountID = accountId, transactionID = null)
            .applyAction()

    fun navigateToEditAccount(accountId: String) =
        NavigationAction.AccountEditor(accountId)
            .applyAction()

    fun navigateToEditTransaction(accountId: String, transactionId: String) =
        NavigationAction.TransactionEditor(accountID = accountId, transactionID = transactionId)
            .applyAction()

}