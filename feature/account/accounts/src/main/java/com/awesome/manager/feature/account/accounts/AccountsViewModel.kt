package com.awesome.manager.feature.account.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.asDataStateFlow
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val accountsState: AccountsState = AccountsState(
        accounts = accountRepository.returnAccounts("")
                .map { DataState.Success(it) }
                .asDataStateFlow(viewModelScope)
    )

}