package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.common.asAmState
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val accountDetailsArg: AccountDetailsRoute = savedStateHandle.toRoute()
    private val accountID: String = accountDetailsArg.accountID

    private val accountUIState = accountRepository
        .getAccountByID(accountID = accountID)
        .filterNotNull()
        .asAmState(viewModelScope)

    val accountDetailsState: AccountDetailsState = AccountDetailsState(
        setString = { savedStateHandle[this] = it },
        getString = { savedStateHandle.getStateFlow(this, it) },
        refreshTransactions = ::refreshTransactions,
        account = accountUIState,
        transactions = transactionRepository.getTransactionsByAccountID(accountID, ""),
    )

    private fun refreshTransactions() {
        viewModelScope.launch {
            accountDetailsState.apply {
                transactionRepository.refreshTransactions().collectLatest {
//                    when (it) {
//                        is AmUIState.Error -> accountDetailsState.endRefreshing()
//                        is AmUIState.Loading -> accountDetailsState.startRefreshing()
//                        is AmUIState.Success -> accountDetailsState.endRefreshing()
//                    }
                }
            }
        }
    }

}