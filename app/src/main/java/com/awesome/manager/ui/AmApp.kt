package com.awesome.manager.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.awesome.manager.feature.auth.AuthRoute
import com.awesome.manager.feature.auth.navigateToAuth
import com.awesome.manager.feature.home.HomeRoute
import com.awesome.manager.feature.home.navigateToHome
import com.awesome.manager.feature.transaction.transactions.navigateToTransactions
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.MainDestination
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmApp() {

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navHostController = rememberNavController(bottomSheetNavigator)
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val mainActivityViewModel: MainActivityViewModel = viewModel()


    val currentBackStack: NavBackStackEntry? =
        navHostController.currentBackStackEntryAsState().value
    val currentMainDestination: MainDestination? = remember(currentBackStack?.destination) {
        MainDestination.entries.firstOrNull {
            currentBackStack?.destination?.hasRoute(it.route) == true
        }
    }

    val isLoginStateValue = mainActivityViewModel.isLogin.collectAsStateWithLifecycle().value
    LaunchedEffect(isLoginStateValue, currentBackStack) {
        currentBackStack?.let { currentBackStack ->
            val inAuthScreen = currentBackStack.destination.hasRoute(AuthRoute::class) == true
            val inHomeScreen = currentBackStack.destination.hasRoute(HomeRoute::class) == true
            Timber.d("TEST_AUTH $isLoginStateValue $inAuthScreen $inHomeScreen ${currentBackStack.destination}")
            navHostController.navigateByAuthState(
                isLogin = isLoginStateValue,
                inAuthScreen = inAuthScreen,
                inHomeScreen = inHomeScreen,
            )
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
//            floatingActionButtonPosition = FabPosition.End,
//            floatingActionButton = {
//                ExtendedFloatingActionButton(
//                    icon = { AmIcon(amIconsType = AmIcons.AccountAdd) },
//                    text = { AmText(text="ADD") },
//                    onClick = {},
//
//                )
//            },
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


private fun NavHostController.navigateByAuthState(
    isLogin: Boolean,
    inAuthScreen: Boolean,
    inHomeScreen: Boolean
) = when (isLogin) {
    true -> when (inAuthScreen) {
        true -> {
            val navOptions = navOptions {
                popUpTo(AuthRoute) { inclusive = true }
                launchSingleTop = true
            }
            navigateToHome(navOptions)
        }

        false -> Unit
    }

    false -> when (inAuthScreen) {
        true -> Unit
        false -> when (inHomeScreen) {
            true -> {
                val navOptions = navOptions {
                    popUpTo(HomeRoute) { inclusive = true }
                    launchSingleTop = true
                }
                navigateToAuth(navOptions)
            }

            false -> navigateUp()
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


