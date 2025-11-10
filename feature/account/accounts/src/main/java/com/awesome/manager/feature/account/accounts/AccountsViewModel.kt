package com.awesome.manager.feature.account.accounts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.model.AmAccountWithDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val pagingAccounts: Flow<PagingData<AmAccountWithDetails>> = accountRepository.getAccounts()

    private val _accountsState: MutableStateFlow<AccountsState> =
        MutableStateFlow(AccountsState.Idle)
    val accountsState: StateFlow<AccountsState> = _accountsState.asStateFlow()

    private val _accountsEvent: MutableStateFlow<AccountsEvents> = MutableStateFlow(AccountsEvents.Idle)
    val accountsEvent: StateFlow<AccountsEvents> = _accountsEvent
    fun doneNavigation() = _accountsEvent.update { AccountsEvents.Idle }
    fun navigateTo(navigation: AccountsEvents) = _accountsEvent.update { navigation }

    init {
        refreshAccounts()
    }

    fun refreshAccounts() {
        viewModelScope.launch {
            accountRepository.refreshAccounts().collectLatest {
                when (it) {
                    is AmState.Error -> _accountsState.update { AccountsState.Error }
                    is AmState.Loading -> _accountsState.update { AccountsState.Loading }
                    is AmState.Success -> _accountsState.update { AccountsState.Idle }
                }
            }
        }
    }

}