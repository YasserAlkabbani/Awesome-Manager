package com.awesome.manager.feature.account.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.main.MainState
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


class AccountsState(
    val accounts: Flow<PagingData<AmAccount>>
) : MainState() {


}