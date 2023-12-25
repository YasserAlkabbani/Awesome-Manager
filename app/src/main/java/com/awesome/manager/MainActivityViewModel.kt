package com.awesome.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.transaction_type.TransactionTypeRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val currencyRepository: CurrencyRepository,
    private val transactionTypeRepository: TransactionTypeRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val mainActivityState = MainActivityState(
        isLogin = authRepository.isLogin()
            .onEach { if (it) refreshData() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)
    )

    private fun refreshData() {
        viewModelScope.launch {
            launch { authRepository.refreshUserInfo() }
            launch { currencyRepository.refreshCurrency() }
            launch { transactionTypeRepository.refreshTransactionType() }
            launch {
                accountRepository.refreshAccounts()
                accountRepository.syncAccount()
            }
            launch {
                transactionRepository.refreshTransactions()
                transactionRepository.synTransactions()
            }
        }

    }

}