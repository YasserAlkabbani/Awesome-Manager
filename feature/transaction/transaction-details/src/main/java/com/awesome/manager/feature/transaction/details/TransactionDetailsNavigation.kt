package com.awesome.manager.feature.transaction.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDetailsRoute(val accountID: String, val transactionID: String)

fun NavController.navigateToTransactionDetails(
    accountID: String,
    transactionID: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = TransactionDetailsRoute(
            accountID = accountID,
            transactionID = transactionID
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.transactionDetailsScreen(
    navigateToAccountDetails: (accountID: String) -> Unit,
    navigateToTransactionEditor: (accountID:String,transactionID: String) -> Unit,
) {
    composable<TransactionDetailsRoute> {
        TransactionDetailsRoute(
            navigateToAccountDetails = navigateToAccountDetails,
            navigateToTransactionEditor = navigateToTransactionEditor,
        )
    }
}