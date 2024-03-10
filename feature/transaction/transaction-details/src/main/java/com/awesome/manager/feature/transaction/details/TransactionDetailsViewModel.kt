package com.awesome.manager.feature.transaction.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.asDataStateFlow
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.feature.transaction.details.navigation.TransactionDetailsArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val transactionId: String = TransactionDetailsArg(savedStateHandle).transactionId

    val transactionDetailsState: TransactionDetailsState = TransactionDetailsState(

        transaction = transactionRepository.returnTransactionById(transactionId)
            .map { transaction -> DataState.Success(transaction) }
            .asDataStateFlow(viewModelScope),
        account = transactionRepository.returnTransactionById(transactionId)
            .flatMapLatest { transaction ->
                accountRepository.returnAccountById(transaction.accountId)
                    .map { account -> DataState.Success(account) }
            }.asDataStateFlow(viewModelScope)
    )


}