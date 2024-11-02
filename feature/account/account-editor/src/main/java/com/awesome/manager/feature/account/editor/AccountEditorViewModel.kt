package com.awesome.manager.feature.account.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.model.UpsertAccount
import com.awesome.manager.core.ui.actions.asUIState
import com.awesome.manager.core.ui.actions.main.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    savedStateHandle: SavedStateHandle,
    currencyRepository: CurrencyRepository,
) : ViewModel() {

    private val accountEditorArg: NavigationAction.AccountEditor = savedStateHandle.toRoute()
    private val accountID = accountEditorArg.accountId

    val accountEditorState: AccountEditorState = AccountEditorState(
        currencies = currencyRepository.returnCurrencies().asUIState(viewModelScope),
        accountEditorData = authRepository.currentUserId().flatMapLatest { currentUserID ->
            when (accountID) {
                null -> flowOf(
                    AccountEditorData.asCreate(currentUserID)
                )

                else -> accountRepository
                    .returnAccountById(accountID).map { account ->
                        AccountEditorData.asEdit(account)
                    }
            }
        }.asUIState(viewModelScope),
        onUpsert = ::onUpsert,
        savedStateHandle = savedStateHandle,
    )

    init {
        viewModelScope.launch {
            launch {
                accountEditorState.syncAccountData()
            }
            launch {
                accountEditorState.syncUIState()
            }
        }
    }

    private fun onUpsert(upsertAccount: UpsertAccount) {
        viewModelScope.launch {
            accountRepository.upsertAccount(upsertAccount)
            accountEditorState.navigatePopBack()
        }
    }

}