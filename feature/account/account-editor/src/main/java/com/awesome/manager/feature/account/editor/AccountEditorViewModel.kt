package com.awesome.manager.feature.account.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertAccount
import com.awesome.manager.core.ui.actions.asUIState
import com.awesome.manager.core.ui.actions.main.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val savedStateHandle: SavedStateHandle,
    currencyRepository: CurrencyRepository,
) : ViewModel() {

    private val accountEditorArg: NavigationAction.AccountEditor = savedStateHandle.toRoute()
    private val accountID = accountEditorArg.accountId

    private val accountEditorData: StateFlow<AmUIState<AccountEditorData>> =
        when (accountID) {
            null -> authRepository
                .currentUser()
                .map { user -> AccountEditorData.create(user.id) }

            else -> accountRepository
                .getAccountByID(accountID = accountID)
                .filterNotNull()
                .map { account -> AccountEditorData.create(account) }
        }.asUIState(viewModelScope)

    val accountEditorState: AccountEditorState = AccountEditorState(
        setString = { savedStateHandle[this] = it },
        getString = { savedStateHandle.getStateFlow(this, it) },
        currencies = currencyRepository.returnCurrencies().asUIState(viewModelScope),
        transactionTypes = AmTransactionType.getTypes(),
        accountEditorData = accountEditorData,
        upsertAccount = { upsertAccount() },
    )

    private fun UpsertAccount.upsertAccount() {
        viewModelScope.launch {
            accountRepository.upsertAccount(this@upsertAccount)
            accountEditorState.navigateToAccountDetails(id)
        }
    }

}