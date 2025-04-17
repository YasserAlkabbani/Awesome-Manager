package com.awesome.manager.feature.transaction.details

import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.common.filterSuccessData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach

class TransactionDetailsState(
    val setString: String.(value: String) -> Unit,
    val getString: String.(defaultValue: String) -> StateFlow<String>,
    val transaction: StateFlow<AmState<AmTransaction>>,
    val account: StateFlow<AmState<AmAccount>>
) {

    val transactionDetailsUI = transaction.filterSuccessData().processUIState()


    private fun Flow<AmTransaction>.processUIState() = onEach { transaction ->
//        dynamicFabTransactionDetails(
//            hasEditTransactionPermission = transaction.updatePermission,
//            navigateToEditTransaction = {
//                navigateToEditTransaction(
//                    accountId = transaction.accountID, transactionId = transaction.transactionID
//                )
//            },
//            popUp = ::navigatePopBack
//        )
    }

}