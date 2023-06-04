package com.awesome.manager.feature.accounts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.accounts.AccountsRoute
import kotlinx.coroutines.flow.StateFlow

const val accountRoute="accounts_route"

fun NavController.navigateToAccounts(navOptions: NavOptions?){
    navigate(route = accountRoute,navOptions=navOptions)
}

fun NavGraphBuilder.accountsScreen(
    clickFab: StateFlow<Unit?>,
    doneClickFab:()->Unit,
){
    composable(route= accountRoute){
        AccountsRoute(
            clickFab=clickFab,
            doneClickFab=doneClickFab
        )
    }
}