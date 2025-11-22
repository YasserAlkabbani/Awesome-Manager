package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import com.awesome.manager.core.common.UIStates
import com.awesome.manager.core.common.asStateFlowValue
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmAccountWithDetails
import com.awesome.manager.core.model.AmTransactionWithDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AccountDetailsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val accountDetailsArg: AccountDetailsRoute = savedStateHandle.toRoute()
    private val accountID: String = accountDetailsArg.accountID

    val accountTransactionsPaging: Flow<PagingData<AmTransactionWithDetails>> =
        transactionRepository.returnTransactionsByAccountID(accountID, "")
            .onStart { refreshTransactions() }

    internal val accountWithDetails: StateFlow<AmAccountWithDetails?> = accountRepository
        .getAccountByID(accountID = accountID)
        .onStart { refreshAccount() }
        .asStateFlowValue(scope = viewModelScope)

    private val _accountDetailsUIState: MutableStateFlow<UIStates> =
        MutableStateFlow(UIStates.Loading)
    val accountDetailsUIState: StateFlow<UIStates> =
        _accountDetailsUIState.asStateFlow()

    private val _transactionsUIState: MutableStateFlow<UIStates> =
        MutableStateFlow(UIStates.Loading)
    val transactionsUIState: StateFlow<UIStates> =
        _transactionsUIState.asStateFlow()


    private val _accountDetailsEvent: MutableStateFlow<AccountDetailsEvent> =
        MutableStateFlow(AccountDetailsEvent.Idle)
    val accountDetailsEvent: StateFlow<AccountDetailsEvent> = _accountDetailsEvent

    fun doneAccountDetailsEvent() =
        _accountDetailsEvent.update { AccountDetailsEvent.Idle }

    fun navigateToCreateTransaction(account: AmAccount) =
        _accountDetailsEvent.update { AccountDetailsEvent.CreateTransactionNavigation(account) }

    fun navigateToEditAccount(account: AmAccount) =
        _accountDetailsEvent.update { AccountDetailsEvent.EditAccountNavigation(account) }

    fun navigateBack() =
        _accountDetailsEvent.update { AccountDetailsEvent.PopupNavigation }


    fun refreshAccount() {
        viewModelScope.launch {
            accountRepository.refreshAccounts()
                .map { it.asUIState() }
                .collectLatest { uiState -> _transactionsUIState.update { uiState } }
        }
    }

    fun refreshTransactions() {
        viewModelScope.launch {
            transactionRepository.refreshTransactions()
                .map { it.asUIState() }
                .collectLatest { uiState -> _transactionsUIState.update { uiState } }
        }
    }

}