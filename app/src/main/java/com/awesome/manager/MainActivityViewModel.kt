package com.awesome.manager

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val currencyRepository: CurrencyRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val mainActivityState = MainActivityActions(
        setString = { savedStateHandle[this] = it },
        getString = { savedStateHandle.getStateFlow(this, it) },
        isLogin = authRepository.isLogin()
            .onEach { if (it) refreshData() else clearData() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, null),
        currentUser = authRepository.currentUser()
            .stateIn(viewModelScope, SharingStarted.Eagerly, null),
        logout = ::logout
    )

    private fun logout() {
        viewModelScope.launch {
            mainActivityState.dismissBottomSheet()
            authRepository.logout().collect()
        }
    }

    private fun clearData() {
        viewModelScope.launch {
            accountRepository.deleteAccounts()
            transactionRepository.deleteTransactions()
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            launch {
                Timber.d("TEST_AM I")
                currencyRepository.refreshCurrency().collect()
                Timber.d("TEST_AM II")
                accountRepository.refreshAccounts().collect()
                Timber.d("TEST_AM III")
                transactionRepository.refreshTransactions().collect()
                Timber.d("TEST_AM VI")
            }
            launch {
                accountRepository.syncAccount()
            }
            launch {
                transactionRepository.synTransactions()
            }
        }
    }

}