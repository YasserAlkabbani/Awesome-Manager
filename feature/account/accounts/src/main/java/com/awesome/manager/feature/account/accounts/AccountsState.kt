package com.awesome.manager.feature.account.accounts


enum class AccountsState{
    IDLE,
    LOADING,
    ERROR
}

sealed interface AccountsNavigation{
    data object CreateAccount:AccountsNavigation
    data class Account(val accountID: String):AccountsNavigation
    data class CreateTransaction(val accountID: String):AccountsNavigation
}