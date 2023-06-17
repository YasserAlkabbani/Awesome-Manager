package com.awesome.manager.feature.account.accounts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.account.accounts.AccountsRoute

const val accountsRoute="accounts_route"

fun NavHostController.navigateToAccounts(navOptions: NavOptions?){
    navigate(route = accountsRoute,navOptions=navOptions)
}

fun NavGraphBuilder.accountsScreen(
    navigateToAccountDetails:(String)->Unit,
    navigateToCreateAccount:()->Unit,
){
    composable(route= accountsRoute){
        AccountsRoute(
            navigateToAccountDetails=navigateToAccountDetails,
            navigateToCreateAccount=navigateToCreateAccount,
        )
    }
}