package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.ViewModel
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TransactionsViewModel @Inject constructor(
    transactionRepository: TransactionRepository
) : ViewModel() {

    val transactionsState: TransactionsMainState = TransactionsMainState(
        transactions = transactionRepository.returnTransactions("")
    )

}

