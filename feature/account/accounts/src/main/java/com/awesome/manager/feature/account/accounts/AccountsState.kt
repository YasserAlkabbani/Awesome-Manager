package com.awesome.manager.feature.account.accounts

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.main.MainState
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.StateFlow


class AccountsState(
    val accounts: StateFlow<DataState<List<AmAccount>>>
) : MainState() {


}