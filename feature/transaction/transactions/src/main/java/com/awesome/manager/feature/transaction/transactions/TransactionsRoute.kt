package com.awesome.manager.feature.transaction.transactions

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object TransactionRoute


fun NavController.navigateToTransactions(navOptions: NavOptions?) {
    navigate(
        route = TransactionRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.transactionsScreen(
    navigateToCreateTransaction: () -> Unit,
    navigateToTransactionDetails: (accountID: String, transactionID: String) -> Unit,
) {
    composable<TransactionRoute> {
        TransactionsRoute(
            navigateToCreateTransaction = navigateToCreateTransaction,
            navigateToTransactionDetails = navigateToTransactionDetails
        )
    }
}