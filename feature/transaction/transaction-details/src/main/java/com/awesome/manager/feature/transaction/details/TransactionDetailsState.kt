package com.awesome.manager.feature.transaction.details

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.actions.main.StateManager
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.StateFlow

class TransactionDetailsState(
    val transactionDetailsData: StateFlow<DataState<TransactionDetailsData>>
) : StateManager() {


}

data class TransactionDetailsData(
    val account: AmAccount,
    val transaction: AmTransaction,
    val allowToUpdate: Boolean
)