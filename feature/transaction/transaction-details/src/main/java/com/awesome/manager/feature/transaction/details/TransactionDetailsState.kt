package com.awesome.manager.feature.transaction.details

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.MainActionsState
import com.awesome.manager.core.designsystem.ui_actions.NavigationAction
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TransactionDetailsState(
    val account: StateFlow<DataState<AmAccount>>,
    val transaction: StateFlow<DataState<AmTransaction>>
) : MainActionsState() {


}