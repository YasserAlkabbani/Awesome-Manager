package com.awesome.manager.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.ProcessStates
import com.awesome.manager.core.common.ProcessStates.Loading
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

//    val balanceDetails: StateFlow<AmState<List<BalanceDetails>>> = currencyRepository
//        .returnBalanceDetails()
//        .asAmState(viewModelScope)

    private val _currencies: MutableStateFlow<ProcessStates<Unit>> =
        MutableStateFlow(Loading())
    val currencies: StateFlow<ProcessStates<Unit>> = _currencies.asStateFlow()

    private val _accounts: MutableStateFlow<ProcessStates<Unit>> = MutableStateFlow(Loading())
    val accounts: StateFlow<ProcessStates<Unit>> = _accounts.asStateFlow()

    private val _transactions: MutableStateFlow<ProcessStates<Unit>> =
        MutableStateFlow(Loading())
    val transactions: StateFlow<ProcessStates<Unit>> = _transactions.asStateFlow()

    init {
        refreshData()
    }

    private fun refreshData() {
        refreshAccounts()
        refreshTransactions()
    }

    private fun refreshAccounts() = viewModelScope.launch {
        accountRepository.refreshAccounts().collect { accounts ->
            _accounts.update { accounts }
        }
    }

    private fun refreshTransactions() = viewModelScope.launch {
        transactionRepository.refreshTransactions().collect()
    }

}