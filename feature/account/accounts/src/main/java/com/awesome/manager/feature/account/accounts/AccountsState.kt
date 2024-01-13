package com.awesome.manager.feature.account.accounts

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

const val ACCOUNT_SEARCH_KEY: String = "ACCOUNT_SEARCH_KEY"


class AccountsState(
    private val savedStateHandle: SavedStateHandle,
    asAccounts: StateFlow<String>.() -> StateFlow<List<AmAccount>>
) {

    private val _createAccountNavigation: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val createAccountNavigation: StateFlow<Boolean> = _createAccountNavigation
    fun startCreateAccountNavigation() {
        _createAccountNavigation.update { true }
    }

    fun doneCreateAccountNavigation() {
        _createAccountNavigation.update { false }
    }

    private val _accountDetailsNavigation: MutableStateFlow<AmAccount?> = MutableStateFlow(null)
    val accountDetailsNavigation: StateFlow<AmAccount?> = _accountDetailsNavigation
    fun startAccountDetailsNavigation(amAccount: AmAccount) {
        _accountDetailsNavigation.update { amAccount }
    }

    fun doneAccountDetailsNavigation() {
        _accountDetailsNavigation.update { null }
    }

    private val _createTransactionNavigation: MutableStateFlow<AmAccount?> = MutableStateFlow(null)
    val createTransactionNavigation: StateFlow<AmAccount?> = _createTransactionNavigation
    fun startCreateTransactionNavigation(amAccount: AmAccount?) {
        _createTransactionNavigation.update { amAccount }
    }

    fun doneCreateTransactionNavigation() {
        _createTransactionNavigation.update { null }
    }

    val accountSearchKey: StateFlow<String> = savedStateHandle.getStateFlow(ACCOUNT_SEARCH_KEY, "")
    fun updateAccountSearchKey(newSearchKey: String) {
        savedStateHandle[ACCOUNT_SEARCH_KEY] = newSearchKey
    }

    val accounts: StateFlow<List<AmAccount>> = accountSearchKey.asAccounts()

    private val _profileBottomSheet:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val profileBottomSheet:StateFlow<Boolean> =_profileBottomSheet
    fun showProfileBottomSheet(){_profileBottomSheet.update { true }}
    fun doneProfileBottomSheet(){_profileBottomSheet.update { false }}

}