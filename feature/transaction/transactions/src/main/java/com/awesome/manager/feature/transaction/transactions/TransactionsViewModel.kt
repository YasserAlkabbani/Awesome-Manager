package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TransactionsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    transactionRepository: TransactionRepository
):ViewModel() {

    val transactionState:TransactionState= TransactionState(
        savedStateHandle = savedStateHandle,
        coroutineScope = viewModelScope,
        transactionsAsFlow = transactionRepository.returnTransactions()
    )

}

