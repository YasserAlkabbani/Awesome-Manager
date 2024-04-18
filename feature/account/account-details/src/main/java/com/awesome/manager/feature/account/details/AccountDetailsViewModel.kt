package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.asDataStateFlow
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.feature.account.details.navigation.AccountDetailsArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val accountDetailsArg: AccountDetailsArg = AccountDetailsArg(savedStateHandle)

    val accountDetailsState: AccountDetailsState = AccountDetailsState(
        amAccount = accountRepository
            .returnAccountById(accountDetailsArg.accountId)
            .map { DataState.Success(it) }.asDataStateFlow(viewModelScope),
        amTransactions = transactionRepository
            .returnTransactionsByAccountId(accountDetailsArg.accountId, "")
            .map { DataState.Success(it) }.asDataStateFlow(viewModelScope),
        allowToUpdate = accountRepository
            .returnAccountById(accountDetailsArg.accountId).flatMapLatest { account ->
                authRepository.currentUserId().map { it == account.creatorUserId }
            }
            .map { DataState.Success(it) }.asDataStateFlow(viewModelScope),
    )

}