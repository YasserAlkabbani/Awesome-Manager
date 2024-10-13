package com.awesome.manager.feature.account.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
) : ViewModel() {

    val accountsState: AccountsActions = AccountsActions(
        searchForAccounts = {
            accountRepository.returnAccounts(this.searchKey)
        },
        refreshAccounts = ::refreshAccounts
    )

    private fun refreshAccounts() {
        viewModelScope.launch {
            accountsState.apply {
                accountRepository::refreshAccounts
            }
        }
    }

}