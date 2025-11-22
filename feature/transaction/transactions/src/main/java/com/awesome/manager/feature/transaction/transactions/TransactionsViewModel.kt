package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.UIStates
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
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
class TransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val pagingTransactions =
        transactionRepository.returnTransactions().onStart { refreshTransactions() }

    private val _transactionsState: MutableStateFlow<UIStates> = MutableStateFlow(UIStates.Loading)
    val transactionsUIState: StateFlow<UIStates> = _transactionsState.asStateFlow()

    private val _transactionsEvent: MutableStateFlow<TransactionsEvents> =
        MutableStateFlow(TransactionsEvents.Idle)
    val transactionsEvent: StateFlow<TransactionsEvents> =
        _transactionsEvent.asStateFlow()

    fun doneTransactionsEvent() =
        _transactionsEvent.update { TransactionsEvents.Idle }

    fun navigateToCreateTransaction() =
        _transactionsEvent.update { TransactionsEvents.CreateTransactionNavigation }

    fun navigateToTransactionDetails(transaction: AmTransaction) =
        _transactionsEvent.update {
            TransactionsEvents.TransactionDetailsNavigation(transaction = transaction)
        }


    fun refreshTransactions() {
        viewModelScope.launch {
            transactionRepository.refreshTransactions()
                .map { it.asUIState() }
                .collectLatest { uIStates -> _transactionsState.update { uIStates } }
        }
    }

}

