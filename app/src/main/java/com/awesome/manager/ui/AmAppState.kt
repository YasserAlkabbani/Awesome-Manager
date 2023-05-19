package com.awesome.manager.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.awesome.manager.feature.accounts.navigation.accountRoute
import com.awesome.manager.feature.home.navigation.homeRoute
import com.awesome.manager.feature.transactions.navigation.transactionsRoute
import com.awesome.manager.navigation.MainDestination
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberAmAppState(
    coroutineScope:CoroutineScope= rememberCoroutineScope(),
    navHostController: NavHostController= rememberNavController()
):AmAppState{
    return remember(coroutineScope,navHostController) {
        AmAppState(coroutineScope,navHostController)
    }
}

@Stable
class AmAppState(
    val coroutineScope: CoroutineScope,
    val navHostController: NavHostController,
) {

    val currentDistanation: NavDestination?
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination


    val currentMainDestination
        @Composable get() = when (currentDistanation?.route) {
            homeRoute -> MainDestination.Home
            accountRoute -> MainDestination.Accounts
            transactionsRoute -> MainDestination.Transactions
            else -> null
        }

    val shouldShowBottomBar
        @Composable get() = currentMainDestination != null

    val shouldShowShowTopBar
        @Composable get() = currentMainDestination == null

    val mainDestination = MainDestination.values().toList()

    fun navigateToMainDestination(mainDestination: MainDestination) {

        val MainDestinationNavOption = navOptions{
            popUpTo(navHostController.graph.findStartDestination().id){
                saveState=true
            }
            launchSingleTop=true
            restoreState=true
        }

        when(mainDestination){
            MainDestination.Home -> navHostController.navigate(homeRoute)
            MainDestination.Accounts -> navHostController.navigate(accountRoute)
            MainDestination.Transactions -> navHostController.navigate(transactionsRoute)
        }

    }

}