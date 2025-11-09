package com.awesome.manager.feature.account.accounts


enum class AccountsState{
    IDLE,
    LOADING,
    ERROR
}

sealed interface AccountsEvent{
    data object Idle:AccountsEvent
    data object CreateAccount:AccountsEvent
    data class Account(val accountID: String):AccountsEvent
    data class CreateTransaction(val accountID: String):AccountsEvent
}