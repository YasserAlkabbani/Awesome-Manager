package com.awesome.manager.feature.transaction.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.asDataStateFlow
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
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
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val transactionId: String = TransactionDetailsArg(savedStateHandle).transactionId

    val transactionDetailsState: TransactionDetailsState = TransactionDetailsState(
        transactionDetailsData = transactionRepository.returnTransactionById(transactionId)
            .flatMapLatest { transaction ->
                accountRepository.returnAccountById(transaction.accountId)
                    .map { account -> account to transaction }
            }
            .flatMapLatest { (account, transaction) ->
                authRepository.currentUserId()
                    .map { currentUserId -> Triple(account, transaction, currentUserId) }
            }
            .map { (account, transaction, currentUserId) ->
                DataState.Success(
                    TransactionDetailsData(
                        account = account, transaction = transaction,
                        allowToUpdate = transaction.creatorUserId == currentUserId
                    )
                )
            }
            .asDataStateFlow(viewModelScope)
    )


}