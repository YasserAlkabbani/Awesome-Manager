package com.awesome.manager.feature.account.accounts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository
) : ViewModel() {

    val accountsState: AccountsState =
        AccountsState(
            savedStateHandle = savedStateHandle,
            asAccounts = {
                flatMapLatest { accountRepository.returnAccounts(it) }
                    .flowOn(Dispatchers.Default)
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())
            }
        )


}