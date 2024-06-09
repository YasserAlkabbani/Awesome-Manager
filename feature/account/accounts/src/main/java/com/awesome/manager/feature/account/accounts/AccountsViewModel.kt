package com.awesome.manager.feature.account.accounts

import androidx.lifecycle.ViewModel
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    accountRepository: AccountRepository,
) : ViewModel() {

    val accountsState: AccountsMainState = AccountsMainState(
        searchForAccounts = {
            accountRepository.returnAccounts(this.searchKey)
        }
    )

}