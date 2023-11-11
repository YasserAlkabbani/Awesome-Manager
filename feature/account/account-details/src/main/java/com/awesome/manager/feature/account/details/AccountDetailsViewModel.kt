package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.feature.account.details.navigation.AccountDetailsArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val accountDetailsArg: AccountDetailsArg = AccountDetailsArg(savedStateHandle)

    val accountDetailsState: AccountDetailsState = AccountDetailsState(
        amAccount = accountRepository.returnAccountById(accountDetailsArg.accountId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null),
        amTransactions = transactionRepository.returnTransactionsByAccountId(accountDetailsArg.accountId,"")
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf()),
    )

}