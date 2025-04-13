package com.awesome.manager.feature.account.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update


class AccountsState(
    val setString: String.(value: String) -> Unit,
    val getString: String.(defaultValue: String) -> StateFlow<String>,
    val pagingAccounts: Flow<PagingData<AmAccount>>,
    val refreshAccounts: () -> Unit,
) {

}