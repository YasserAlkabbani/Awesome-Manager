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
import com.awesome.manager.feature.account.accounts.navigation.accountsRoute
import com.awesome.manager.feature.account.accounts.navigation.navigateToAccounts
import com.awesome.manager.feature.account.details.navigation.accountDetailsRoute
import com.awesome.manager.feature.account.editor.navigation.accountEditorRoute
import com.awesome.manager.feature.account.editor.navigation.navigateToCreateAccount
import com.awesome.manager.feature.auth.navigation.authRoute
import com.awesome.manager.feature.auth.navigation.navigateToAuth
import com.awesome.manager.feature.home.navigation.homeRoute
import com.awesome.manager.feature.home.navigation.navigateToHome
import com.awesome.manager.feature.intro.navigation.introRoute
import com.awesome.manager.feature.intro.navigation.navigateToIntro
import com.awesome.manager.feature.transaction.details.navigation.transactionDetailsRoute
import com.awesome.manager.feature.transaction.editor.navigation.navigateToCreateTransaction
import com.awesome.manager.feature.transaction.editor.navigation.transactionEditorRoute
import com.awesome.manager.feature.transaction.transactions.navigation.navigateToTransactions
import com.awesome.manager.feature.transaction.transactions.navigation.transactionsRoute
import com.awesome.manager.navigation.MainDestination
import kotlinx.coroutines.CoroutineScope


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
//            homeRoute -> MainDestination.Home
            accountsRoute -> MainDestination.Accounts
            transactionsRoute -> MainDestination.Transactions
            else -> null
        }

    val shouldShowBottomBar
        @Composable get() = currentMainDestination != null

//    val shouldShowFloatingActionButton
//        @Composable get() = when(currentDestination?.route){
//            accountsRoute, accountDetailsRoute ->true
//            else -> false
//        }

    val shouldShowShowTopBar
        @Composable get() = when(currentDestination?.route){
//            homeRoute, accountsRoute,transactionsRoute, accountEditorRoute ->true
            else -> false
        }

    val shouldShowShowTopBarSave
        @Composable get() = when(currentDestination?.route){
            accountEditorRoute, transactionEditorRoute ->true
            else -> false
        }

    val shouldShowShowTopBarEdit
        @Composable get() = when(currentDestination?.route){
            accountDetailsRoute, transactionDetailsRoute ->true
            else -> false
        }

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
//            MainDestination.Home ->
//                navHostController.navigateToHome(navOptions = mainDestinationNavOption)

            MainDestination.Accounts ->
                navHostController.navigateToAccounts(navOptions = mainDestinationNavOption)

            MainDestination.Transactions ->
                navHostController.navigateToTransactions(navOptions = mainDestinationNavOption)
        }
    }

    fun navigateByAuthState(isLogin: Boolean, currentDestinationRoute: String) {
        when (isLogin) {
            true -> when (currentDestinationRoute) {
                authRoute -> {
                    val homeNavOption = navOptions {
                        popUpTo(authRoute) { this.inclusive = true }
                    }
                    navHostController.navigateToHome( homeNavOption)
                }
                introRoute -> {
                    val homeNavOption = navOptions {
                        popUpTo(introRoute) { this.inclusive = true }
                    }
                    navHostController.navigateToHome(homeNavOption)
                }
            }
            false -> {
                when (currentDestinationRoute) {
                    authRoute -> {}
                    introRoute -> {
                        val homeNavOption = navOptions {
                            popUpTo(introRoute) { this.inclusive = true }
                        }
                        navHostController.navigateToAuth(homeNavOption)
                    }
                    homeRoute -> {
                        val authNavOption = navOptions {
                            popUpTo(homeRoute) { this.inclusive = true }
                        }
                        navHostController.navigateToIntro(authNavOption)
                    }
                    else -> {
                        navHostController.popBackStack()
                    }
                }
            }
        }
    }

    fun navigateToAddByCurrentNavigation(currentDestinationRoute: String){
        when(currentDestinationRoute){
            homeRoute->navHostController.navigateToCreateAccount(null)
            accountsRoute->navHostController.navigateToCreateAccount(null)
            transactionsRoute->navHostController.navigateToCreateTransaction(null,null)
        }
    }

}