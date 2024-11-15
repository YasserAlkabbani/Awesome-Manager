package com.awesome.manager.feature.transaction.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.ui.actions.asUIState
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.ui.actions.main.NavigationAction
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

    private val transactionDetails: NavigationAction.TransactionDetails =
        savedStateHandle.toRoute()

    val transactionDetailsState: TransactionDetailsActions = TransactionDetailsActions(
        transactionDetailsData = transactionRepository.returnTransactionById(transactionDetails.transactionId)
            .flatMapLatest { transaction ->
                    accountRepository.returnAccountById(transaction.accountId)
                        .map { account -> account to transaction }
            }
            .map { (account, transaction) ->
                    TransactionDetailsData(
                        account = account, transaction = transaction,
                        allowToUpdate = transaction.updatePermission
                    )
            }
            .asUIState(viewModelScope)
    )
}