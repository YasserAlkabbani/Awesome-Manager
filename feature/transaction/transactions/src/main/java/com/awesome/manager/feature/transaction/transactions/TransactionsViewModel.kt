package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val transactionsState: TransactionsState = TransactionsState(
        setString = { savedStateHandle[this] = it },
        getString = { savedStateHandle.getStateFlow(this, it) },
        refreshTransactions = ::refreshTransactions,
        pagingTransactions = transactionRepository.getTransactions()
    )

    private fun refreshTransactions() {
        viewModelScope.launch {
            transactionRepository.refreshTransactions().collectLatest {
                when (it) {
                    is AmUIState.Error -> transactionsState.endRefreshing()
                    is AmUIState.Loading -> transactionsState.startRefreshing()
                    is AmUIState.Success -> transactionsState.endRefreshing()
                }
            }
        }
    }

}

