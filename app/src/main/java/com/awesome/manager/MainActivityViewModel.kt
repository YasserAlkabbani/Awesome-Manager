package com.awesome.manager

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.model.AmUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _isLogin: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLogin: StateFlow<Boolean> = _isLogin.asStateFlow()

    private val _currentUser: MutableStateFlow<AmUser?> = MutableStateFlow(null)
    val currentUser: StateFlow<AmUser?> = _currentUser

    init {
        syncData()
    }

    private fun syncData() {
        syncLoginState()
        syncCurrentUser()
        syncPendingAccounts()
        syncPendingTransactions()
    }


    private fun syncLoginState() = viewModelScope.launch {
        authRepository.isLogin().collect { isLogin ->
            _isLogin.update { isLogin }
        }
    }

    private fun syncCurrentUser() = viewModelScope.launch {
        authRepository.currentUser().collect { currentUser ->
            _currentUser.update { currentUser }
        }
    }

    private fun syncPendingAccounts() = viewModelScope.launch {

    }

    private fun syncPendingTransactions() = viewModelScope.launch {

    }


    private fun logout() {
        viewModelScope.launch {
            authRepository.logout().collect()
        }
    }

    private fun clearData() {
        viewModelScope.launch {
            accountRepository.deleteAllAccounts()
            transactionRepository.deleteTransactions()
        }
    }

}