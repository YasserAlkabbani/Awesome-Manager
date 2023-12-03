package com.awesome.manager.feature.transaction.transactions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.core.designsystem.component.AppBarData
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.feature.transaction.transactions.TransactionsRoute

const val transactionsRoute: String = "transactions_route"

fun NavHostController.navigateToTransactions(navOptions: NavOptions?) {
    navigate(route = transactionsRoute, navOptions = navOptions)
}


fun NavGraphBuilder.transactionsScreen(
    navigateToTransactionDetails: (AmTransaction) -> Unit,
    updateAppBarState: (appBarData: AppBarData?) -> Unit
) {
    composable(transactionsRoute) {
        TransactionsRoute(
            navigateToTransactionDetails = navigateToTransactionDetails,
            updateAppBarState = updateAppBarState,
        )
    }
}