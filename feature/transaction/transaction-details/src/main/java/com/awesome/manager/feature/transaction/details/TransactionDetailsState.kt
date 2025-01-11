package com.awesome.manager.feature.transaction.details

import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.ui.actions.main.ActionsManager
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.ui.actions.filterSuccessData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach

class TransactionDetailsState(
    override val setString: String.(value: String) -> Unit,
    override val getString: String.(defaultValue: String) -> StateFlow<String>,
    val transaction: StateFlow<AmUIState<AmTransaction>>,
    val account: StateFlow<AmUIState<AmAccount>>
) : ActionsManager() {

    val transactionDetailsUI = transaction.filterSuccessData().processUIState()


    private fun Flow<AmTransaction>.processUIState() = onEach { transaction ->
        dynamicFabTransactionDetails(
            hasEditTransactionPermission = transaction.updatePermission,
            navigateToEditTransaction = {
                navigateToEditTransaction(
                    accountId = transaction.accountID, transactionId = transaction.transactionID
                )
            },
            popUp = ::navigatePopBack
        )
    }

}