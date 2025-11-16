package com.awesome.manager.feature.account.accounts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.awesome.manager.core.common.UIStates
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmAccountWithDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val pagingAccounts: Flow<PagingData<AmAccountWithDetails>> =
        accountRepository.getAccounts().onStart { refreshAccounts() }

    private val _accountsUIStates: MutableStateFlow<UIStates> = MutableStateFlow(UIStates.Loading)
    val accountsUIStates: StateFlow<UIStates> = _accountsUIStates.asStateFlow()

    private val _accountsEvent: MutableStateFlow<AccountsEvents> =
        MutableStateFlow(AccountsEvents.Idle)
    val accountsEvent: StateFlow<AccountsEvents> = _accountsEvent.asStateFlow()
    fun doneAccountsEvents() = _accountsEvent.update { AccountsEvents.Idle }
    fun navigateToCreateAccount() = _accountsEvent.update { AccountsEvents.CreateAccountNavigation }
    fun navigateToAccountDetails(account: AmAccount) =
        _accountsEvent.update { AccountsEvents.AccountDetailsNavigation(account) }

    fun navigateToCreateTransaction(account: AmAccount) =
        _accountsEvent.update { AccountsEvents.CreateTransactionNavigation(account) }

    fun refreshAccounts() {
        viewModelScope.launch {
            accountRepository.refreshAccounts()
                .map { it.asUIState() }
                .collectLatest { uiState -> _accountsUIStates.update { uiState } }
        }
    }

}