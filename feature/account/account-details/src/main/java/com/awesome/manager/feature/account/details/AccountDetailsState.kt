package com.awesome.manager.feature.account.details

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.MainActionsState
import com.awesome.manager.core.designsystem.ui_actions.NavigationAction
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.StateFlow

class AccountDetailsState(
    val amAccount: StateFlow<DataState<AmAccount>>,
    val amTransactions: StateFlow<DataState<List<AmTransaction>>>
): MainActionsState() {



}