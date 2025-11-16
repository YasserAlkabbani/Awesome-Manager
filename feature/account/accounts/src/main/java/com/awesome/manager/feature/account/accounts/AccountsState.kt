package com.awesome.manager.feature.account.accounts

import com.awesome.manager.core.model.AmAccount


sealed interface AccountsState

sealed interface AccountsEvents{
    data object Idle:AccountsEvents
    data object CreateAccountNavigation:AccountsEvents
    data class AccountDetailsNavigation(val account: AmAccount):AccountsEvents
    data class CreateTransactionNavigation(val account: AmAccount):AccountsEvents
}