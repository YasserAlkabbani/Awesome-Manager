package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.model.AmTransactionWithDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AccountDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val accountDetailsArg: AccountDetailsRoute = savedStateHandle.toRoute()
    private val accountID: String = accountDetailsArg.accountID

    internal val accountDetailsState: StateFlow<AccountDetailsState> = accountRepository
        .getAccountByID(accountID = accountID)
        .onStart { refreshTransactions() }
        .map { AccountDetailsState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = AccountDetailsState.Loading,
            started = SharingStarted.Eagerly
        )

    private val _accountTransactionsState: MutableStateFlow<AccountTransactionsState> =
        MutableStateFlow(AccountTransactionsState.Loading)
    val accountTransactionsState: StateFlow<AccountTransactionsState> =
        _accountTransactionsState

    val accountTransactionsPaging: Flow<PagingData<AmTransactionWithDetails>> =
        transactionRepository
            .getTransactionsByAccountID(accountID, "")

    private val _accountDetailsEvent: MutableStateFlow<AccountDetailsEvent> =
        MutableStateFlow(AccountDetailsEvent.Idle)
    val accountDetailsEvent: StateFlow<AccountDetailsEvent> = _accountDetailsEvent

    fun navigateToCreateTransaction() =
        _accountDetailsEvent.update { AccountDetailsEvent.CreateTransaction(accountID) }

    fun navigateToEditAccount() =
        _accountDetailsEvent.update { AccountDetailsEvent.EditAccount(accountID) }

    fun navigateBack() =
        _accountDetailsEvent.update { AccountDetailsEvent.Popup }

    fun accountDetailsEventDone() =
        _accountDetailsEvent.update { AccountDetailsEvent.Idle }

    fun refreshTransactions() {
        viewModelScope.launch {
            transactionRepository.refreshTransactions().collectLatest { refreshingState ->
                _accountTransactionsState.update {
                    when (refreshingState) {
                        is AmState.Error -> AccountTransactionsState.Error
                        is AmState.Loading -> AccountTransactionsState.Loading
                        is AmState.Success<*> -> AccountTransactionsState.Success
                    }
                }
            }
        }
    }

}