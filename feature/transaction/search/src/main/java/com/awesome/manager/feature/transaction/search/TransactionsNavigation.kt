package com.awesome.manager.feature.transaction.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.core.model.AmTransaction

const val transactionsRoute:String="transactions_route"

fun NavHostController.navigateToTransactions(navOptions: NavOptions?){
    navigate(route = transactionsRoute,navOptions=navOptions)
}


fun NavGraphBuilder.transactionsScreen(onSelectTransaction:(AmTransaction)->Unit){
    composable(transactionsRoute){
        TransactionsRoute(onSelectTransaction=onSelectTransaction)
    }
}