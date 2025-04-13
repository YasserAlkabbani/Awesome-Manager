package com.awesome.manager.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.awesome.manager.MainActivityViewModel
import com.awesome.manager.feature.account.accounts.navigateToAccounts
import com.awesome.manager.feature.home.navigateToHome
import com.awesome.manager.feature.transaction.transactions.navigateToTransactions
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.MainDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmApp() {

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navHostController = rememberNavController(bottomSheetNavigator)
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val mainActivityState = mainActivityViewModel.mainActivityState

    val currentUser = mainActivityState.currentUser.collectAsState().value
    val loginState = mainActivityState.isLogin.collectAsState().value

    val currentBackStack: NavBackStackEntry? =
        navHostController.currentBackStackEntryAsState().value
    val currentDestination =
        remember(currentBackStack) { currentBackStack?.destination }
    val currentMainDestination: MainDestination? = remember(currentDestination) {
        MainDestination.entries.firstOrNull {
            currentDestination?.hasRoute(it.route) == true
        }
    }

    AppScreen(
        navHostController = navHostController,
        bottomSheetNavigator = bottomSheetNavigator,
        currentMainDestination = currentMainDestination,
    )

}

@Composable
fun AppScreen(
    navHostController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    currentMainDestination: MainDestination?,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButtonPosition = FabPosition.End,
            bottomBar = {
                AmBottomNavigation(
                    currentMainDestination = currentMainDestination,
                    navigateTo = navHostController::navigateToMainDestination
                )
            }
        ) { padding ->
            AmNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .imePadding(),
                navHostController = navHostController,
                bottomSheetNavigator = bottomSheetNavigator
            )
        }
    }

}


private fun NavHostController.navigateToMainDestination(mainDestination: MainDestination) {
    val navOptions = navOptions {
        popUpTo(id = graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    when (mainDestination) {
        MainDestination.Home -> navigateToHome(navOptions)
        MainDestination.Accounts -> navigateToAccounts(navOptions)
        MainDestination.Transactions -> navigateToTransactions(navOptions)
    }
}





