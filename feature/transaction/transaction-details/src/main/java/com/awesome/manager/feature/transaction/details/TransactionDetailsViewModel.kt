package com.awesome.manager.feature.transaction.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.feature.transaction.details.navigation.TransactionDetailsArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
            .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), null),
        asAccount = {
            flatMapLatest { accountRepository.returnAccountById(it?.accountId) }
                .flowOn(Dispatchers.Default)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    null
                )
        }
    )


}