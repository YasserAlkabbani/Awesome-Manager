package com.awesome.manager.feature.account.accounts


sealed interface AccountsState{
    data object Idle:AccountsState
    data object Loading:AccountsState
    data object Error:AccountsState
}

sealed interface AccountsEvents{
    data object Idle:AccountsEvents
    data object NavigationCreateAccount:AccountsEvents
    data class NavigationAccountDetails(val accountID: String):AccountsEvents
    data class NavigationCreateTransaction(val accountID: String):AccountsEvents
}