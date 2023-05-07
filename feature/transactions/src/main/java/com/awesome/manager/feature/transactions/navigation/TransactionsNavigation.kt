package com.awesome.manager.feature.transactions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.transactions.TransactionsRoute

const val transactionsRoute:String="transactions_route"

fun NavHostController.navigateToTransactions(navOptions: NavOptions?){
    navigate(route = transactionsRoute,navOptions=navOptions)
}


fun NavGraphBuilder.transactionsScreen(){
    composable(transactionsRoute){
        TransactionsRoute()
    }
}