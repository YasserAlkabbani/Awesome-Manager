package com.awesome.manager.feature.transaction.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.transaction.details.TransactionDetailsRoute

const val transactionDetailsRoute:String="transaction_details_route"

fun NavHostController.navigateToTransactionDetails(navOptions: NavOptions?) {
    navigate(route = transactionDetailsRoute,navOptions = navOptions)
}

fun NavGraphBuilder.transactionDetailsScreen(){
    composable(transactionDetailsRoute){
        TransactionDetailsRoute()
    }
}