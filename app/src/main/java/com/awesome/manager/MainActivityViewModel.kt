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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authRepository:AuthRepository,
    private val currencyRepository: CurrencyRepository,
    private val transactionTypeRepository: TransactionTypeRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
):ViewModel() {

    val mainActivityState=MainActivityState(
        isLogin = authRepository.isLogin().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),false)
    )

    init {
        sync()
    }

    fun sync(){
        observeAuthTokenState()
        viewModelScope.launch {
            refreshData()
        }

    }

    private fun observeAuthTokenState(){
        viewModelScope.launch {
            authRepository.isLogin().distinctUntilChanged().collectLatest {
                Timber.d("TEST_AUTH AUTH_TOKEN $it")
//                if (it) refreshData()
            }
        }
    }

    private suspend fun refreshData(){
        Timber.d("TEST_AUTH REFRESH_DATA")
//            launch { authRepository.refreshUserInfo() }

        coroutineScope {
            launch {currencyRepository.refreshCurrency()}
            launch {transactionTypeRepository.refreshTransactionType()}
            launch {accountRepository.syncAccount()}
            launch {transactionRepository.refreshTransactions()}
        }

    }

}