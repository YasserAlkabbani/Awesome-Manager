package com.awesome.manager.feature.transaction.transactions

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.MainActionsState
import com.awesome.manager.core.designsystem.ui_actions.NavigationAction
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.StateFlow


class TransactionsState(
    val transactions:StateFlow<DataState<List<AmTransaction>>>
): MainActionsState() {


}