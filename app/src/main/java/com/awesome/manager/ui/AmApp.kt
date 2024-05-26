package com.awesome.manager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.awesome.manager.MainActivityViewModel
import com.awesome.manager.core.designsystem.UIConstant
import com.awesome.manager.core.designsystem.ui_actions.bottomsheet.BottomSheetAction
import com.awesome.manager.core.designsystem.component.AmNavigationCustomItem
import com.awesome.manager.core.designsystem.component.AmCustomBottomBarWithFab
import com.awesome.manager.core.designsystem.ui_actions.navigation.MainDistillation
import com.awesome.manager.core.designsystem.ui_actions.navigation.NavigationAction
import com.awesome.manager.core.designsystem.ui_actions.navigation.NavigationDestination
import com.awesome.manager.core.designsystem.ui_actions.picker.PickerAction
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetProfile
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetSearchForAccount
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetAccountCreated
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetAuthError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetConnectionError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetCustomError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetPasswordRestered
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetUnknownError
import com.awesome.manager.core.ui.dialog.AmDatePickerDialog
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.asNavigationDestination
import kotlinx.coroutines.launch
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmApp() {
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val mainActivityState = mainActivityViewModel.mainActivityState

    val currentUserEmail = mainActivityState.currentUserEmail.collectAsState().value
    val loginState = mainActivityState.isLogin.collectAsState().value

    val appBarState = mainActivityState.appBarAction.collectAsState().value

    val navHostController = rememberNavController()

    val currentBackStack: NavBackStackEntry? =
        navHostController.currentBackStackEntryAsState().value
    val currentNavigationDestination = remember(currentBackStack) {
        currentBackStack?.asNavigationDestination()
    }


    LaunchedEffect(key1 = loginState, key2 = currentNavigationDestination, block = {
        Timber.d("TEST_NAVIGATION $loginState $currentBackStack")
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
    })


    val navigationState = mainActivityState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationState, block = {
        navigationState?.let {
            mainActivityState.resetNavigationAction()
            when (navigationState) {
                NavigationAction.PopBack -> {
                    navHostController.popBackStack()
                }

                is NavigationAction.Navigate -> {
                    val mainDestinationNavOption =
                        when (navigationState.navigationDestination.isMainDistinction()) {
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
                        navigationState,
                        navOptions = mainDestinationNavOption
                    )
                }
            }
        }
    })

    val bottomSheetState = mainActivityState.bottomSheetAction.collectAsState().value
    val sheetState: SheetState = rememberModalBottomSheetState(true)
    LaunchedEffect(key1 = bottomSheetState, block = {
        when (bottomSheetState) {
            is BottomSheetAction.Idle<*> -> Unit
            is BottomSheetAction.Dismiss<*> -> launch {
                sheetState.hide()
                mainActivityState.idleBottomSheet(clearBottomSheet = true)
            }

            is BottomSheetAction.AccountCreated, is BottomSheetAction.AuthError,
            is BottomSheetAction.ConnectionError, is BottomSheetAction.CustomError,
            is BottomSheetAction.PasswordRested, is BottomSheetAction.Profile,
            is BottomSheetAction.SearchForAccount, is BottomSheetAction.UnknownError -> {
                sheetState.show()
                mainActivityState.idleBottomSheet()
            }
        }
    })


    if (!bottomSheetState.isEmpty()) {
        ModalBottomSheet(
            modifier = Modifier
                .padding(horizontal = UIConstant.PADDING_LOW.dp)
                .requiredHeightIn(max = 500.dp),
            onDismissRequest = { mainActivityState.dismissBottomSheet() },
            sheetState = sheetState,
            properties = ModalBottomSheetDefaults.properties(shouldDismissOnBackPress = bottomSheetState.isDismissible),
            content = {
                Column(
                    modifier = Modifier.padding(6.dp),
                    content = { bottomSheetState.content() }
                )
            }
        )
    }


    val pickActionState = mainActivityState.pickAction.collectAsState().value
    when (pickActionState) {
        PickerAction.Idle -> Unit
        is PickerAction.PickDate -> {
            AmDatePickerDialog(pickDate = pickActionState)
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                AmCustomBottomBarWithFab(
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
                                        mainActivityState.updateMainState(
                                            navigationDestination.asMainAction()
                                        )
                                    }
                                )
                            }
                    },
                    appBarAction = appBarState
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
        ) { padding ->
            AmNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .statusBarsPadding()
                    .imePadding(),
                navHostController = navHostController,
                sendMainAction = mainActivityState::updateMainState
            )
        }
    }

}

@Composable
private fun BottomSheetAction.content(): Unit =
    when (this) {
        is BottomSheetAction.Idle<*> -> bottomSheet!!.content()

        is BottomSheetAction.Dismiss<*> -> bottomSheet.content()

        is BottomSheetAction.Profile -> BottomSheetProfile(this)

        is BottomSheetAction.SearchForAccount -> BottomSheetSearchForAccount(this)

        is BottomSheetAction.AccountCreated -> BottomSheetAccountCreated(this)

        is BottomSheetAction.PasswordRested -> BottomSheetPasswordRestered(this)

        is BottomSheetAction.AuthError -> BottomSheetAuthError(this)

        is BottomSheetAction.UnknownError -> BottomSheetUnknownError(this)

        is BottomSheetAction.ConnectionError -> BottomSheetConnectionError(this)

        is BottomSheetAction.CustomError -> BottomSheetCustomError(this)

    }