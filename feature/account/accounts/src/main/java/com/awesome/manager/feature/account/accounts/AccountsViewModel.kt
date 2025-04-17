package com.awesome.manager.feature.account.accounts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val accountsState: AccountsState = AccountsState(
        setString = { savedStateHandle[this] = it },
        getString = { savedStateHandle.getStateFlow(this, it) },
        pagingAccounts = accountRepository.getAccounts(),
        refreshAccounts = ::refreshAccounts
    )

    init {
        refreshAccounts()
    }

    private fun refreshAccounts() {
        viewModelScope.launch {
            accountRepository.refreshAccounts().collectLatest {
//                when (it) {
//                    is AmUIState.Error -> accountsState.endRefreshing()
//                    is AmUIState.Loading -> accountsState.startRefreshing()
//                    is AmUIState.Success -> accountsState.endRefreshing()
//                }
            }
        }
    }

}