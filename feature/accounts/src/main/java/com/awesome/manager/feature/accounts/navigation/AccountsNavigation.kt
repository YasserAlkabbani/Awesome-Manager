package com.awesome.manager.feature.accounts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.accounts.AccountsRoute

const val accountRoute="accounts_route"

fun NavController.navigateToAccounts(navOptions: NavOptions?){
    navigate(route = accountRoute,navOptions=navOptions)
}

fun NavGraphBuilder.accountScreen(){
    composable(route= accountRoute){
        AccountsRoute()
    }
}