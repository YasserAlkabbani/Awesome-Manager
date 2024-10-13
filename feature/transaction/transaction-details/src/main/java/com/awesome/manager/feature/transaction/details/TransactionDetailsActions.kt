package com.awesome.manager.feature.transaction.details

import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.ui.actions.main.ActionsManager
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.StateFlow

class TransactionDetailsActions(
    val transactionDetailsData: StateFlow<AmUIState<TransactionDetailsData>>
) : ActionsManager() {


}

data class TransactionDetailsData(
    val account: AmAccount,
    val transaction: AmTransaction,
    val allowToUpdate: Boolean
)