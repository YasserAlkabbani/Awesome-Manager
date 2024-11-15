package com.awesome.manager.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.awesome.manager.MainActivityViewModel
import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmNavigationBar
import com.awesome.manager.core.designsystem.component.AmNavigationItem
import com.awesome.manager.core.designsystem.component.AmSpacerLargeWidth
import com.awesome.manager.core.ui.actions.main.DynamicFabAction
import com.awesome.manager.core.ui.actions.main.BottomSheetAction
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.ui.actions.main.NavigationAction
import com.awesome.manager.core.designsystem.component.dynamic_bar.AmDynamicFab
import com.awesome.manager.core.designsystem.component.dynamic_bar.AmDynamicBarLoading
import com.awesome.manager.core.designsystem.component.dynamic_bar.AmDynamicText
import com.awesome.manager.core.designsystem.component.dynamic_bar.AmDynamicFabExtraButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.AmFabButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFab
import com.awesome.manager.core.ui.actions.main.ErrorAction
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetDatePicker
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetDateRangePicker
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetProfile
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetAccountCreated
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetAuthError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetConnectionError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetCustomError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetPasswordRestored
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetUnknownError
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.MainDestination
import com.awesome.manager.navigation.asNavigationDestination
import com.awesome.manager.navigation.isMainDistinction
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmApp() {

    val navHostController = rememberNavController()
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val mainActivityState = mainActivityViewModel.mainActivityState

    val currentUser = mainActivityState.currentUser.collectAsState().value
    val loginState = mainActivityState.isLogin.collectAsState().value


    val dynamicFabState = remember { mutableStateOf<DynamicFabAction>(DynamicFabAction.None) }
    val bottomSheetSate = remember { mutableStateOf<BottomSheetAction>(BottomSheetAction.Dismiss) }

    val mainAction = mainActivityState.mainAction.collectAsState().value
    mainAction?.let {
        LaunchedEffect(key1 = mainAction) {
            mainActivityState.doneMainAction()
            Timber.d("TEST_MAIN_ACTION $mainAction")
            when (mainAction) {
                is DynamicFabAction -> dynamicFabState.value = mainAction
                is BottomSheetAction -> {
                    when (mainAction) {
                        is BottomSheetAction.Dismiss -> launch { sheetState.hide() }
                            .invokeOnCompletion { bottomSheetSate.value = mainAction }

                        else -> bottomSheetSate.value = mainAction
                    }
                }

                is NavigationAction -> {
                    val mainDestinationNavOption =
                        when (mainAction.isMainDistinction()) {
                            true -> navOptions {
                                popUpTo(NavigationAction.Home) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                            false -> null
                        }
                    when (mainAction) {
                        NavigationAction.NavigateUp -> navHostController.navigateUp()
                        else -> navHostController.navigate(
                            route = mainAction,
                            navOptions = mainDestinationNavOption
                        )
                    }
                }

                is ErrorAction -> {
                    Timber.d("TEST_MAIN_ACTION ${mainAction.amUIError}")
                    when (mainAction.amUIError) {
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
    }

    val currentBackStack: NavBackStackEntry? =
        navHostController.currentBackStackEntryAsState().value
    val currentNavigationDestination = remember(currentBackStack) {
        currentBackStack?.asNavigationDestination()
    }
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

                NavigationAction.Home -> mainActivityState.dynamicFab(
                    dynamicFab = DynamicFab.Profile(
                        onClick = {
                            currentUser?.let { email ->
                                mainActivityState.showProfileBottomSheet(
                                    email = currentUser.email, logout = mainActivityState.logout
                                )
                            }
                        }
                    ),
                    dynamicFabExtraButton = null
                )

                NavigationAction.Accounts -> mainActivityState.dynamicFab(
                    dynamicFab = DynamicFab.AddAccount(
                        onClick = mainActivityState::navigateToCreateAccount
                    )
                )

                NavigationAction.Transactions -> mainActivityState.dynamicFab(
                    dynamicFab = DynamicFab.AddTransaction(
                        onClick = mainActivityState::navigateToCreateAccount
                    )
                )

                is NavigationAction.AccountDetails -> Unit
                is NavigationAction.AccountEditor -> Unit
                is NavigationAction.TransactionDetails -> Unit
                is NavigationAction.TransactionEditor -> Unit
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    bottomSheetSate.value.takeIf { it !is BottomSheetAction.Dismiss }?.let { bottomSheetAction ->
        Timber.d("TEST_MAIN_ACTION BOTTOM_SHEET SHOW $bottomSheetAction")
        ModalBottomSheet(
            modifier = Modifier
                .padding(horizontal = AmPadding.SMALL.value)
                .requiredHeightIn(max = 500.dp),
            onDismissRequest = mainActivityState::dismissBottomSheet,
            sheetState = sheetState,
            properties = ModalBottomSheetProperties(),
            content = {
                Column(
                    modifier = Modifier.padding(6.dp),
                    content = { bottomSheetAction.Content() }
                )
            }
        )
    }

    dynamicFabState.value.let { dynamicFab ->
        AppScreen(
            navHostController = navHostController,
            currentNavigation = currentNavigationDestination,
            dynamicFabAction = dynamicFab,
            updateMainAction = mainActivityState::updateMainState,
        )
    }

}

@Composable
fun AppScreen(
    navHostController: NavHostController,
    currentNavigation: NavigationAction?,
    dynamicFabAction: DynamicFabAction, updateMainAction: (MainAction) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = { dynamicFabAction.Content() },
            floatingActionButtonPosition = FabPosition.End,
            bottomBar = {
                AmNavigationBar(
                    visible = currentNavigation?.isMainDistinction() == true
                ) {
                    MainDestination.entries.forEach { destination ->
                        val navigationDestination = destination.navigationDestination
                        AmNavigationItem(
                            isSelected = navigationDestination == currentNavigation,
                            title = stringResource(destination.title),
                            selectedIcon = destination.selectedAmIconsType,
                            unSelectedIcon = destination.unSelectedAmIconsType,
                            onSelect = { updateMainAction(navigationDestination) }
                        )
                    }
                }
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
}

@Composable
private fun BottomSheetAction.Content(): Unit =
    when (this) {
        is BottomSheetAction.AccountCreated -> BottomSheetAccountCreated()
        is BottomSheetAction.AuthError -> BottomSheetAuthError()
        is BottomSheetAction.ConnectionError -> BottomSheetConnectionError()
        is BottomSheetAction.CustomError -> BottomSheetCustomError()
        is BottomSheetAction.PasswordRested -> BottomSheetPasswordRestored()
        is BottomSheetAction.Profile -> BottomSheetProfile()
        is BottomSheetAction.UnknownError -> BottomSheetUnknownError()
        is BottomSheetAction.PickDate -> BottomSheetDatePicker()
        is BottomSheetAction.PickRangeDate -> BottomSheetDateRangePicker()
        BottomSheetAction.Dismiss -> Unit
    }

@Composable
private fun DynamicFabAction.Content(): Unit =
    AnimatedContent(
        targetState = this@Content,
        label = "DYNAMIC_BAR",
        transitionSpec = {
            val initDynamicFab: DynamicFabAction = initialState
            val targetDynamicFab: DynamicFabAction = targetState
            val indexDifferance = (initDynamicFab.index - targetDynamicFab.index).absoluteValue
            when (indexDifferance) {
                0 -> {
                    slideInHorizontally { width -> 0 } togetherWith
                            slideOutHorizontally { width -> 0 }
                }

                in 1..100 -> {
                    when (initDynamicFab.index > targetDynamicFab.index) {
                        true -> slideInVertically { height -> height } + fadeIn() togetherWith
                                slideOutVertically { height -> -height } + fadeOut()

                        false -> slideInVertically { height -> -height } + fadeIn() togetherWith
                                slideOutVertically { height -> height } + fadeOut()
                    }
                }

                else -> {
                    when (initDynamicFab.index > targetDynamicFab.index) {
                        true -> slideInHorizontally { width -> width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> -width } + fadeOut()

                        false -> slideInHorizontally { width -> -width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> width } + fadeOut()
                    }
                }
            }.using(
                SizeTransform(clip = false)
            )
        }
    ) { dynamicFabAction ->
        Row(
            modifier = Modifier.height(AmSize.XXX_LARGE.value),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            dynamicFabAction.dynamicFabExtraButton?.let { dynamicFabExtraButton ->
                AmDynamicFabExtraButton(dynamicFabExtraButton)
                AmSpacerLargeWidth()
            }
            when (dynamicFabAction) {

                DynamicFabAction.None -> {}

                DynamicFabAction.Loading -> AmDynamicBarLoading()


                is DynamicFabAction.Fab -> AmDynamicFab(dynamicFabAction.dynamicFab)

                is DynamicFabAction.Button -> AmFabButton(dynamicFabAction.dynamicFabButton)

                is DynamicFabAction.Message -> AmDynamicText(dynamicFabAction.dynamicFabText)


            }
        }
    }


