package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class TransactionsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    transactionRepository: TransactionRepository
):ViewModel() {

    val transactionsState:TransactionsState= TransactionsState(
        savedStateHandle = savedStateHandle,
        asTransactions = {
            flatMapLatest {
                transactionRepository.returnTransactions(it)
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())
        }
    )

}

