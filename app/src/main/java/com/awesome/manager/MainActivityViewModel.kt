package com.awesome.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.transaction_type.TransactionTypeRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.designsystem.ui_actions.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
            .onEach {
                if (it) refreshData() else clearData()
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false),
        currentUserEmail = authRepository.currentUserEmail()
            .stateIn(viewModelScope, SharingStarted.Eagerly, ""),
        logout = ::logout
    )

    private fun logout() {
        viewModelScope.launch {
//            mainActivityState.sendMainAction(mainAction = BottomSheetAction.Close().asMainAction())
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
                authRepository.refreshUserInfo()
                currencyRepository.refreshCurrency()
                transactionTypeRepository.refreshTransactionType()
                accountRepository.refreshAccounts()
                transactionRepository.refreshTransactions()
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