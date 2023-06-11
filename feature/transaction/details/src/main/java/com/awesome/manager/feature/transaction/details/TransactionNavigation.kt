package com.awesome.manager.feature.transaction.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val transactionRoute:String="transaction_route"

fun NavHostController.navigateToTransaction(navOptions: NavOptions?) {
    navigate(route = com.awesome.manager.feature.transaction.details.transactionRoute,navOptions = navOptions)
}

fun NavGraphBuilder.transactionScreen(){
    composable(com.awesome.manager.feature.transaction.details.transactionRoute){

    }
}