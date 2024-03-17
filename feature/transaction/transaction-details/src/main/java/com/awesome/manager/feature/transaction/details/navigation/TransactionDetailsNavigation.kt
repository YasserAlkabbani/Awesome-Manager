package com.awesome.manager.feature.transaction.details.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.feature.transaction.details.TransactionDetailsRoute

const val transactionDetailsRoute: String = "transaction_details_route"
const val ARG_TRANSACTION_ID: String = "transaction_id"

internal class TransactionDetailsArg(val transactionId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(Uri.decode(savedStateHandle[ARG_TRANSACTION_ID]))
}

fun NavHostController.navigateToTransactionDetails(
    transactionId: String, navOptions: NavOptions? = null
) {
    navigate(
        route = "$transactionDetailsRoute/$transactionId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.transactionDetailsScreen(sendMainAction: (MainActions) -> Unit) {
    composable("$transactionDetailsRoute/{$ARG_TRANSACTION_ID}") {
        TransactionDetailsRoute(
            sendMainAction = sendMainAction
        )
    }
}