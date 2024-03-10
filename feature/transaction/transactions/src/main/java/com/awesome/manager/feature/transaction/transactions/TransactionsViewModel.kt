package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.asDataStateFlow
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class TransactionsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    transactionRepository: TransactionRepository
) : ViewModel() {

    val transactionsState: TransactionsState = TransactionsState(
        transactions = transactionRepository.returnTransactions("").map {
            DataState.Success(it)
        }.asDataStateFlow(viewModelScope)
    )

}

