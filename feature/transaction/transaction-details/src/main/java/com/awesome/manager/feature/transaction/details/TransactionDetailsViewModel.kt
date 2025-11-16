package com.awesome.manager.feature.transaction.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.common.UIStates
import com.awesome.manager.core.common.asStateFlowValue
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
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
class TransactionDetailsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val transactionDetails: TransactionDetailsRoute = savedStateHandle.toRoute()
    private val transactionID: String = transactionDetails.transactionID
    private val accountID: String = transactionDetails.accountID

    val account = accountRepository
        .getAccountByID(accountID)
        .onStart { refreshAccounts() }
        .asStateFlowValue(viewModelScope)

    val transaction = transactionRepository
        .returnTransactionByID(transactionID)
        .onStart { refreshTransactions() }
        .asStateFlowValue(viewModelScope)

    private val _accountUIState: MutableStateFlow<UIStates> = MutableStateFlow(UIStates.Loading)
    val accountUIState: StateFlow<UIStates> = _accountUIState.asStateFlow()

    private val _transactionUIState: MutableStateFlow<UIStates> = MutableStateFlow(UIStates.Loading)
    val transactionUIState: StateFlow<UIStates> = _transactionUIState.asStateFlow()

    private val _transactionDetailsActions: MutableStateFlow<TransactionDetailsActions> =
        MutableStateFlow(TransactionDetailsActions.Idle)
    val transactionDetailsActions: StateFlow<TransactionDetailsActions> =
        _transactionDetailsActions.asStateFlow()

    fun doneTransactionsDetailsAction() =
        _transactionDetailsActions.update { TransactionDetailsActions.Idle }

    fun navigateToTransactionDetails(transaction: AmTransaction) =
        _transactionDetailsActions.update {
            TransactionDetailsActions.TransactionEditorNavigation(transaction)
        }

    fun navigateToAccountDetails(account: AmAccount) =
        _transactionDetailsActions.update {
            TransactionDetailsActions.AccountDetailsNavigation(account)
        }

    private fun refreshAccounts() {
        viewModelScope.launch {
            accountRepository.refreshAccounts()
                .map { it.asUIState() }
                .collectLatest { uiState -> _accountUIState.update { uiState } }
        }
    }


    private fun refreshTransactions() {
        viewModelScope.launch {
            transactionRepository.refreshTransactions()
                .map { it.asUIState() }
                .collectLatest { uiStates -> _transactionUIState.update { uiStates } }
        }
    }


}