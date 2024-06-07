package com.awesome.manager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.awesome.manager.MainActivityViewModel
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.actions.bottomsheet.BottomSheetContent
import com.awesome.manager.core.designsystem.actions.main.AppBarAction
import com.awesome.manager.core.designsystem.actions.main.BottomSheetAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.main.NavigationAction
import com.awesome.manager.core.designsystem.component.AmNavigationCustomItem
import com.awesome.manager.core.designsystem.component.AmDynamicBottomBar
import com.awesome.manager.core.designsystem.actions.navigation.MainDistillation
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetDatePicker
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetDateRangePicker
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetProfile
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetSearchForAccount
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetAccountCreated
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetAuthError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetConnectionError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetCustomError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetPasswordRestored
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetUnknownError
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.asNavigationDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmApp() {

    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val mainActivityState = mainActivityViewModel.mainActivityState

    val currentUserEmail = mainActivityState.currentUserEmail.collectAsState().value
    val loginState = mainActivityState.isLogin.collectAsState().value

    val navHostController = rememberNavController()

    val navigationAction = mainActivityState.navigationAction.collectAsState().value
    val currentBackStack: NavBackStackEntry? =
        navHostController.currentBackStackEntryAsState().value
    val currentNavigationDestination = remember(currentBackStack) {
        currentBackStack?.asNavigationDestination()
    }
    LaunchedEffect(key1 = loginState, key2 = currentNavigationDestination, block = {
        loginState?.let {
            when (loginState) {
                true -> when (currentNavigationDestination) {
                    NavigationDestination.Auth -> {
                        val homeNavOption = navOptions {
                            popUpTo(NavigationDestination.Auth) { this.inclusive = true }
                        }
                        navHostController.navigate(
                            route = NavigationDestination.Home,
                            navOptions = homeNavOption
                        )
                    }

                    NavigationDestination.Intro -> {
                        val navOption = navOptions {
                            popUpTo(NavigationDestination.Intro) { this.inclusive = true }
                        }
                        navHostController.navigate(
                            route = NavigationDestination.Home,
                            navOptions = navOption
                        )
                    }

                    else -> Unit
                }

                false -> {
                    when (currentNavigationDestination) {
                        NavigationDestination.Auth -> {}
                        NavigationDestination.Intro -> {
                            val navOption = navOptions {
                                popUpTo(NavigationDestination.Intro) { this.inclusive = true }
                            }
                            navHostController.navigate(
                                route = NavigationDestination.Auth,
                                navOptions = navOption
                            )
                        }

                        NavigationDestination.Home -> {
                            val authNavOption = navOptions {
                                popUpTo(NavigationDestination.Home) { this.inclusive = true }
                            }
                            navHostController.navigate(
                                route = NavigationDestination.Intro,
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
                NavigationDestination.Auth -> Unit
                NavigationDestination.Intro -> Unit

                NavigationDestination.Home -> {}
                NavigationDestination.Accounts -> {}
                NavigationDestination.Transactions -> {}

                is NavigationDestination.AccountDetails -> Unit
                is NavigationDestination.AccountEditor -> Unit
                is NavigationDestination.TransactionDetails -> Unit
                is NavigationDestination.TransactionEditor -> Unit
            }
        }
    }
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction?.let {
            mainActivityState.doneNavigationAction()
            when (navigationAction) {
                NavigationAction.PopBack -> {
                    navHostController.popBackStack()
                }

                is NavigationAction.Navigate -> {
                    val mainDestinationNavOption =
                        when (navigationAction.navigationDestination.isMainDistinction()) {
                            true -> navOptions {
                                popUpTo(NavigationDestination.Home) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                            false -> null
                        }
                    navHostController.navigate(
                        navigationAction.navigationDestination,
                        navOptions = mainDestinationNavOption
                    )
                }
            }
        }
    })

    val bottomSheetAction = mainActivityState.bottomSheetAction.collectAsState().value
    val sheetState: SheetState = rememberModalBottomSheetState(true)
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction?.let {
            when (bottomSheetAction) {
                is BottomSheetAction.Dismiss -> launch {
                    sheetState.hide()
                    mainActivityState.doneBottomSheetAction()
                }

                is BottomSheetAction.Open -> {
                    sheetState.show()
                }
            }
        }
    })
    bottomSheetAction?.let {
        ModalBottomSheet(
            modifier = Modifier
                .padding(horizontal = AmPadding.SMALL.value)
                .requiredHeightIn(max = 600.dp),
            onDismissRequest = { mainActivityState.dismissBottomSheet() },
            sheetState = sheetState,
            properties = ModalBottomSheetDefaults.properties(shouldDismissOnBackPress = bottomSheetAction.isDismissible),
            content = {
                Column(
                    modifier = Modifier.padding(6.dp),
                    content = { bottomSheetAction.Content() }
                )
            }
        )
    }


    val pickActionState = mainActivityState.pickerAction.collectAsState().value
    pickActionState?.let {
    }


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val appBarAction = mainActivityState.appBarAction.collectAsState().value
    AppScreen(
        navHostController = navHostController,
        currentNavigationDestination = currentNavigationDestination,
        appBarAction = appBarAction, updateMainAction = mainActivityState::updateMainState
    )

}

@Composable
fun AppScreen(
    navHostController: NavHostController,
    currentNavigationDestination: NavigationDestination?,
    appBarAction: AppBarAction?, updateMainAction: (MainAction) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                appBarAction?.let {
                    AmDynamicBottomBar(
                        modifier = Modifier,
                        bottomBarItems = {
                            MainDistillation.entries
                                .forEach { destination ->
                                    val navigationDestination = destination.navigationDestination
                                    AmNavigationCustomItem(
                                        isSelected = navigationDestination == currentNavigationDestination,
                                        selectedIcon = destination.selectedAmIconsType,
                                        unSelectedIcon = destination.unSelectedAmIconsType,
                                        onSelect = {
                                            updateMainAction(
                                                navigationDestination.asNavigation()
                                            )
                                        }
                                    )
                                }
                        },
                        appBarAction = appBarAction
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
        ) { padding ->
            AmNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                navHostController = navHostController,
                sendMainAction = updateMainAction
            )
        }
    }
}

@Composable
private fun BottomSheetAction.Content(): Unit? =
    content?.let { bottomSheetContent ->
        when (bottomSheetContent) {
            is BottomSheetContent.AccountCreated -> BottomSheetAccountCreated(bottomSheetContent)
            is BottomSheetContent.AuthError -> BottomSheetAuthError(bottomSheetContent)
            is BottomSheetContent.ConnectionError -> BottomSheetConnectionError(bottomSheetContent)
            is BottomSheetContent.CustomError -> BottomSheetCustomError(bottomSheetContent)
            is BottomSheetContent.PasswordRested -> BottomSheetPasswordRestored(bottomSheetContent)
            is BottomSheetContent.Profile -> BottomSheetProfile(bottomSheetContent)
            is BottomSheetContent.UnknownError -> BottomSheetUnknownError(bottomSheetContent)
            is BottomSheetContent.SearchForAccount -> BottomSheetSearchForAccount(bottomSheetContent)
            is BottomSheetContent.PickDate -> BottomSheetDatePicker(bottomSheetContent)
            is BottomSheetContent.PickRangeDate -> BottomSheetDateRangePicker(bottomSheetContent)
        }
    }
