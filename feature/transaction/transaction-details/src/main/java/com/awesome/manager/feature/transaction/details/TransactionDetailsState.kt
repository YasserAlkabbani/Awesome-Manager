package com.awesome.manager.feature.transaction.details

import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.model.AmAccountWithDetails
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionWithDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach

class TransactionDetailsState(
    val setString: String.(String) -> Unit,
    val getString: String.(String) -> StateFlow<String>,
    val transaction: StateFlow<AmState<AmTransactionWithDetails>>,
    val account: StateFlow<AmState<AmAccountWithDetails>>
) {

//    val transactionDetailsUI = transaction.filterSuccess().processUIState()


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