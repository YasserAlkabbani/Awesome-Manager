package com.awesome.manager.feature.transaction.details

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.actions.main.MainState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.StateFlow

class TransactionDetailsState(
    val transactionDetailsData: StateFlow<DataState<TransactionDetailsData>>
) : MainState() {


}

data class TransactionDetailsData(
    val account: AmAccount,
    val transaction: AmTransaction,
    val allowToUpdate: Boolean
)