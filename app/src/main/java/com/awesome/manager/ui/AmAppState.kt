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
import com.awesome.manager.feature.auth.navigation.authRoute
import com.awesome.manager.feature.home.navigation.homeRoute
import com.awesome.manager.feature.intro.navigation.introRoute
import com.awesome.manager.feature.transactions.navigation.transactionsRoute
import com.awesome.manager.navigation.MainDestination
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber


@Composable
fun rememberAmAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navHostController: NavHostController = rememberNavController()
): AmAppState {
    return remember(coroutineScope, navHostController) {
        AmAppState(coroutineScope, navHostController)
    }
}

@Stable
class AmAppState(
    val coroutineScope: CoroutineScope,
    val navHostController: NavHostController,
) {

    val currentDestination: NavDestination?
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination

    val currentMainDestination
        @Composable get() = when (currentDestination?.route) {
            homeRoute -> MainDestination.Home
            accountRoute -> MainDestination.Accounts
            transactionsRoute -> MainDestination.Transactions
            else -> null
        }

    val shouldShowBottomBar
        @Composable get() = currentMainDestination != null

    val shouldShowShowTopBar
        @Composable get() = currentMainDestination == null && currentDestination?.route != authRoute

    val mainDestination = MainDestination.values().toList()

    fun navigateToMainDestination(mainDestination: MainDestination) {
        val mainDestinationNavOption = navOptions {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (mainDestination) {
            MainDestination.Home ->
                navHostController.navigate(homeRoute, navOptions = mainDestinationNavOption)

            MainDestination.Accounts ->
                navHostController.navigate(accountRoute, navOptions = mainDestinationNavOption)

            MainDestination.Transactions ->
                navHostController.navigate(transactionsRoute, navOptions = mainDestinationNavOption)
        }
    }

    fun navigateByAuthState(isLogin: Boolean, currentDestinationRoute: String) {
        Timber.d("TEST_AUTH NAVIGATE_BY_STATE $isLogin $currentDestinationRoute")
        when (isLogin) {
            true -> when (currentDestinationRoute) {
                authRoute -> {
                    val homeNavOption = navOptions {
                        popUpTo(authRoute) { this.inclusive = true }
                    }
                    navHostController.navigate(homeRoute, homeNavOption)
                }
                introRoute -> {
                    val homeNavOption = navOptions {
                        popUpTo(introRoute) { this.inclusive = true }
                    }
                    navHostController.navigate(homeRoute, homeNavOption)
                }
            }
            false -> {
                when (currentDestinationRoute) {
                    authRoute -> {}
                    introRoute -> {
                        val homeNavOption = navOptions {
                            popUpTo(introRoute) { this.inclusive = true }
                        }
                        navHostController.navigate(authRoute, homeNavOption)
                    }
                    homeRoute -> {
                        val authNavOption = navOptions {
                            popUpTo(homeRoute) { this.inclusive = true }
                        }
                        navHostController.navigate(introRoute, authNavOption)
                    }

                    else -> {
                        navHostController.popBackStack()
                    }
                }
            }
        }
    }

}