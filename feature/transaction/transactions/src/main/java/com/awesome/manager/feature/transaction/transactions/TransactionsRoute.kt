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

fun NavGraphBuilder.transactionsScreen() {
    composable<TransactionRoute> {
        TransactionsScreen()
    }
}