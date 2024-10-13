package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val transactionsState: TransactionsActions = TransactionsActions(
        refreshTransactions = ::refreshTransactions,
        searchForTransaction = {
            transactionRepository.returnTransactions(
                searchKey = searchKey, transactionType = transactionType,
                fromDate = date?.first, toDate = date?.second
            )
        }
    )

    private fun refreshTransactions() {
        viewModelScope.launch {
            transactionsState.apply {
                transactionRepository::refreshTransactions
            }
        }
    }

}

