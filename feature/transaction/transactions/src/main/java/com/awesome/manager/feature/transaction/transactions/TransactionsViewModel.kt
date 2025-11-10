package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val pagingTransactions = transactionRepository.getTransactions()

    private val _transactionsState: MutableStateFlow<TransactionsState> =
        MutableStateFlow(TransactionsState.Idle)
    val transactionsState: StateFlow<TransactionsState> = _transactionsState.asStateFlow()

    private val _transactionsEvent: MutableStateFlow<TransactionsEvents> =
        MutableStateFlow(TransactionsEvents.Idle)
    val transactionsEvent: StateFlow<TransactionsEvents> = _transactionsEvent.asStateFlow()

    fun doneTransactionEvent() = _transactionsEvent.update { TransactionsEvents.Idle }
    fun navigateToCreateTransaction() =
        _transactionsEvent.update { TransactionsEvents.NavigationCreateTransaction }
    fun navigateToTransactionDetails(accountID:String, transactionID: String) =
        _transactionsEvent.update { TransactionsEvents.NavigationTransactionDetails(accountID = accountID, transactionID = transactionID) }


    fun refreshTransactions() {
        viewModelScope.launch {
            transactionRepository.refreshTransactions().collectLatest {
                when (it) {
                    is AmState.Error -> TransactionsState.Error
                    is AmState.Loading -> TransactionsState.Loading
                    is AmState.Success<*> -> TransactionsState.Idle
                }
            }
        }
    }

}

