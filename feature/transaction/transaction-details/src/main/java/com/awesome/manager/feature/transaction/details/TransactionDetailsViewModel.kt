package com.awesome.manager.feature.transaction.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.ui.asUIState
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(
    accountRepository: AccountRepository,
    transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val transactionDetails: TransactionDetailsRoute = savedStateHandle.toRoute()
    private val transactionID: String = transactionDetails.transactionID
    private val accountID: String = transactionDetails.accountID

    val transactionDetailsState: TransactionDetailsState = TransactionDetailsState(
        setString = { savedStateHandle[this] = it },
        getString = { savedStateHandle.getStateFlow(this, it) },
        account = accountRepository.getAccountByID(accountID)
            .filterNotNull()
            .asUIState(viewModelScope),
        transaction = transactionRepository.getTransactionById(transactionID)
            .filterNotNull()
            .asUIState(viewModelScope)
    )

}