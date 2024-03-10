package com.awesome.manager.feature.transaction.transactions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.feature.transaction.transactions.TransactionsRoute

const val transactionsRoute: String = "transactions_route"

fun NavHostController.navigateToTransactions(navOptions: NavOptions?) {
    navigate(route = transactionsRoute, navOptions = navOptions)
}


fun NavGraphBuilder.transactionsScreen(sendMainAction :(MainActions)->Unit) {
    composable(transactionsRoute) {
        TransactionsRoute(sendMainAction = sendMainAction)
    }
}