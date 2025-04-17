package com.awesome.manager.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.common.AmState.Loading
import com.awesome.manager.core.common.asAmState
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.BalanceDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val balanceDetails: StateFlow<AmState<List<BalanceDetails>>> = currencyRepository
        .returnBalanceDetails()
        .asAmState(viewModelScope)

    private val _currencies: MutableStateFlow<AmState<List<AmCurrency>>> =
        MutableStateFlow(Loading())
    val currencies: StateFlow<AmState<List<AmCurrency>>> = _currencies.asStateFlow()

    private val _accounts: MutableStateFlow<AmState<List<AmAccount>>> = MutableStateFlow(Loading())
    val accounts: StateFlow<AmState<List<AmAccount>>> = _accounts.asStateFlow()

    private val _transactions: MutableStateFlow<AmState<List<AmTransaction>>> =
        MutableStateFlow(Loading())
    val transactions: StateFlow<AmState<List<AmTransaction>>> = _transactions.asStateFlow()

    init {
        refreshData()
    }

    private fun refreshData() {
        refreshCurrencies()
        refreshAccounts()
        refreshTransactions()
    }

    private fun refreshCurrencies() = viewModelScope.launch {
        currencyRepository.refreshCurrencies().collect { currencies ->
            _currencies.update { currencies }
        }
    }

    private fun refreshAccounts() = viewModelScope.launch {
        accountRepository.refreshAccounts().collect { accounts ->
            _accounts.update { accounts }
        }
    }

    private fun refreshTransactions() = viewModelScope.launch {
        transactionRepository.refreshTransactions().collect { transactions ->
            _transactions.update { transactions }
        }
    }

}