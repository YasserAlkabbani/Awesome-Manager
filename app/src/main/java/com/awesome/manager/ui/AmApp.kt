package com.awesome.manager.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.awesome.manager.MainActivityState
import com.awesome.manager.MainActivityViewModel
import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.ui.actions.main.DynamicFabAction
import com.awesome.manager.core.ui.actions.main.BottomSheetAction
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.ui.actions.main.NavigationAction
import com.awesome.manager.core.ui.actions.main.ErrorAction
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.asNavigationDestination
import com.awesome.manager.navigation.isMainDistinction
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmApp() {

    val navHostController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val mainActivityState = mainActivityViewModel.mainActivityState

    val currentUser = mainActivityState.currentUser.collectAsState().value
    val loginState = mainActivityState.isLogin.collectAsState().value
    val mainAction = mainActivityState.mainAction.collectAsState().value


    val currentBackStack: NavBackStackEntry? =
        navHostController.currentBackStackEntryAsState().value
    val currentNavigationDestination =
        remember(currentBackStack) { currentBackStack?.asNavigationDestination() }
    LaunchedEffect(key1 = loginState, key2 = currentNavigationDestination, block = {
        loginState?.let {
            when (loginState) {
                true -> when (currentNavigationDestination) {
                    NavigationAction.Auth -> {
                        val homeNavOption = navOptions {
                            popUpTo(NavigationAction.Auth) { this.inclusive = true }
                        }
                        navHostController.navigate(
                            route = NavigationAction.Home,
                            navOptions = homeNavOption
                        )
                    }

                    NavigationAction.Intro -> {
                        val navOption = navOptions {
                            popUpTo(NavigationAction.Intro) { this.inclusive = true }
                        }
                        navHostController.navigate(
                            route = NavigationAction.Home,
                            navOptions = navOption
                        )
                    }

                    else -> Unit
                }

                false -> {
                    when (currentNavigationDestination) {
                        NavigationAction.Auth -> {}
                        NavigationAction.Intro -> {
                            val navOption = navOptions {
                                popUpTo(NavigationAction.Intro) { this.inclusive = true }
                            }
                            navHostController.navigate(
                                route = NavigationAction.Auth,
                                navOptions = navOption
                            )
                        }

                        NavigationAction.Home -> {
                            val authNavOption = navOptions {
                                popUpTo(NavigationAction.Home) { this.inclusive = true }
                            }
                            navHostController.navigate(
                                route = NavigationAction.Intro,
                                navOptions = authNavOption
                            )
                        }

                        else -> {
                            navHostController.popBackStack()
                        }
                    }
                }
            }
        }
    })
    LaunchedEffect(key1 = currentNavigationDestination) {
        currentNavigationDestination?.let {
            when (currentNavigationDestination) {
                NavigationAction.NavigateUp -> Unit
                NavigationAction.Auth -> Unit
                NavigationAction.Intro -> Unit
                NavigationAction.Home -> mainActivityState.dynamicFabProfile {
                    currentUser?.let {
                        mainActivityState.showProfileBottomSheet(
                            email = currentUser.email,
                            logout = mainActivityState.logout
                        )
                    }
                }

                NavigationAction.Accounts -> mainActivityState.dynamicFabAddAccount(
                    mainActivityState::navigateToCreateAccount
                )

                NavigationAction.Transactions -> mainActivityState.dynamicFabAddTransaction(
                    { mainActivityState.navigateToCreateTransaction(null) }
                )

                is NavigationAction.AccountDetails -> Unit
                is NavigationAction.AccountEditor -> Unit
                is NavigationAction.TransactionDetails -> Unit
                is NavigationAction.TransactionEditor -> Unit
            }
        }
    }

    val dynamicFabState = remember { mutableStateOf<DynamicFabAction>(DynamicFabAction.None) }
    val bottomSheetSate = remember { mutableStateOf<BottomSheetAction?>(null) }

    mainAction?.let {
        LaunchedEffect(key1 = mainAction) {
            mainActivityState.doneMainAction()
            Timber.d("TEST_MAIN_ACTION MAIN_ACTION $mainAction")
            when (mainAction) {
                is DynamicFabAction -> dynamicFabState.value = mainAction

                is BottomSheetAction -> {
                    when (mainAction) {
                        is BottomSheetAction.SearchForAccount -> mainActivityState.updateSearchKey(
                            mainAction.initSearch
                        )

                        else -> Unit
                    }
                    bottomSheetSate.value = mainAction
                }

                is NavigationAction -> {
                    val destinationNavOption =
                        when {
                            mainAction.isMainDistinction() -> navOptions {
                                popUpTo(NavigationAction.Home) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                            mainAction is NavigationAction.AccountDetails -> navOptions {
                                if (currentNavigationDestination is NavigationAction.AccountEditor) {
                                    popUpTo(currentNavigationDestination) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }

                            mainAction is NavigationAction.TransactionDetails -> navOptions {
                                if (currentNavigationDestination is NavigationAction.TransactionEditor) {
                                    popUpTo(currentNavigationDestination) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }

                            else -> null
                        }
                    when (mainAction) {
                        NavigationAction.NavigateUp -> navHostController.navigateUp()
                        else -> navHostController.navigate(
                            route = mainAction,
                            navOptions = destinationNavOption
                        )
                    }
                }

                is ErrorAction -> when (mainAction.amUIError) {
                    is AmUIError.BadRequest -> Unit
                    is AmUIError.OtherUIError -> Unit
                    AmUIError.ConnectionUIError -> Unit
                    AmUIError.NoDataError -> Unit
                    AmUIError.NoError -> Unit
                    AmUIError.PoorConnection -> Unit
                    AmUIError.Unauthorized -> Unit
                    AmUIError.UnknownUIError -> Unit
                    AmUIError.NoPermissionError -> Unit
                }
            }
        }
    }

    AppScreen(
        mainActivityState = mainActivityState,
        navHostController = navHostController,
        currentNavigation = currentNavigationDestination,
        dynamicFabAction = dynamicFabState.value,
        bottomSheetState = bottomSheetSate.value,
        updateMainAction = mainActivityState::sendMainAction,
        resetBottomSheet = { bottomSheetSate.value = null }
    )

}

@Composable
fun AppScreen(
    mainActivityState: MainActivityState,
    navHostController: NavHostController,
    currentNavigation: NavigationAction?,
    dynamicFabAction: DynamicFabAction,
    bottomSheetState: BottomSheetAction?,
    resetBottomSheet: () -> Unit,
    updateMainAction: (MainAction) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = { dynamicFabAction.Content() },
            floatingActionButtonPosition = FabPosition.End,
            bottomBar = {
                currentNavigation?.AmBottomNavigation(
                    updateNavigation = updateMainAction
                )
            }
        ) { padding ->
            AmNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .imePadding(),
                navHostController = navHostController,
                sendMainAction = updateMainAction
            )
        }
    }

    bottomSheetState?.AmBottomSheet(
        mainActivityState = mainActivityState,
        resetBottomSheet = resetBottomSheet,
    )

}






