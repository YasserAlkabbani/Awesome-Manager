package com.awesome.manager.feature.account.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.model.AmAccountWithBalance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


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