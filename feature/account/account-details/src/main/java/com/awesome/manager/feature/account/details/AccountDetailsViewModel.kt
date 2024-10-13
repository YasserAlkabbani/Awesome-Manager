package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.ui.actions.asDataStateFlow
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.ui.actions.main.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val accountDetailsArg: NavigationAction.AccountDetails = savedStateHandle.toRoute()

    val accountDetailsState: AccountDetailsActions = AccountDetailsActions(
        refreshTransactions = ::refreshTransactions,
        amAccount = accountRepository
            .returnAccountById(accountDetailsArg.accountId)
            .map { AmUIState.Success(it) }.asDataStateFlow(viewModelScope),
        amTransactions = transactionRepository
            .returnTransactionsByAccountId(accountDetailsArg.accountId, ""),
        allowToUpdate = accountRepository
            .returnAccountById(accountDetailsArg.accountId).flatMapLatest { account ->
                authRepository.currentUserId().map { it == account.creatorUserId }
            }
            .map { AmUIState.Success(it) }.asDataStateFlow(viewModelScope),
    )

    private fun refreshTransactions() {
        viewModelScope.launch {
            accountDetailsState.apply {
                transactionRepository::refreshTransactions
            }
        }
    }

}