package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.ui.actions.asUIState
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.ui.actions.main.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val accountDetailsArg: NavigationAction.AccountDetails = savedStateHandle.toRoute()
    private val accountID: String = accountDetailsArg.accountId

    private val accountUIState = accountRepository
        .returnAccountById(accountID = accountID)
        .asUIState(viewModelScope)

    val accountDetailsState: AccountDetailsState = AccountDetailsState(
        setString = { savedStateHandle[this] = it },
        getString = { savedStateHandle.getStateFlow(this, it) },
        refreshTransactions = ::refreshTransactions,
        account = accountUIState,
        transactions = transactionRepository.returnTransactionsByAccountID(accountID, ""),
    )

    private fun refreshTransactions() {
        viewModelScope.launch {
            accountDetailsState.apply {
                transactionRepository.refreshTransactions().collectLatest {
                    when (it) {
                        is AmUIState.Error -> accountDetailsState.endRefreshing()
                        is AmUIState.Loading -> accountDetailsState.startRefreshing()
                        is AmUIState.Success -> accountDetailsState.endRefreshing()
                    }
                }
            }
        }
    }

}