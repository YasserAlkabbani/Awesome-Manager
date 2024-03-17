package com.awesome.manager.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.awesome.manager.MainActivityViewModel
import com.awesome.manager.core.designsystem.ui_actions.BottomSheetAction
import com.awesome.manager.core.designsystem.component.AmAppBar
import com.awesome.manager.core.designsystem.component.AmNavigationCustomItem
import com.awesome.manager.core.designsystem.component.AmCustomBottomBarWithFab
import com.awesome.manager.core.designsystem.ui_actions.NavigationAction
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetProfile
import com.awesome.manager.feature.account.details.navigation.navigateToAccountDetails
import com.awesome.manager.feature.account.editor.navigation.navigateToCreateAccount
import com.awesome.manager.feature.account.editor.navigation.navigateToEditAccount
import com.awesome.manager.feature.auth.navigation.navigateToAuth
import com.awesome.manager.feature.home.navigation.navigateToHome
import com.awesome.manager.feature.intro.navigation.navigateToIntro
import com.awesome.manager.feature.transaction.details.navigation.navigateToTransactionDetails
import com.awesome.manager.feature.transaction.editor.navigation.navigateToCreateTransaction
import com.awesome.manager.feature.transaction.editor.navigation.navigateToEditTransaction
import com.awesome.manager.navigation.AmNavHost
import kotlinx.coroutines.launch
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AmApp(
    maAppState: AmAppState = rememberAmAppState()
) {
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val mainActivityState = mainActivityViewModel.mainActivityState

    val currentUserEmail = mainActivityState.currentUserEmail.collectAsState().value
    val loginState = mainActivityState.isLogin.collectAsState().value

    val appBarState = mainActivityState.appBarAction.collectAsState().value

    val currentDestination = maAppState.currentDestination
    val currentMainDestination = maAppState.currentMainDestination
    val navHostController = maAppState.navHostController


    LaunchedEffect(key1 = loginState, key2 = currentDestination, block = {
        currentDestination?.route?.let {
            maAppState.navigateByAuthState(loginState, it)
        }
    })


    val navigationState = mainActivityState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationState, block = {
        if (navigationState !is NavigationAction.Idle) mainActivityState.resetNavigationAction()
        when (navigationState) {
            NavigationAction.Idle -> {}
            NavigationAction.PopBack -> navHostController.popBackStack()
            NavigationAction.Auth -> navHostController.navigateToAuth()
            NavigationAction.CreateAccount -> navHostController.navigateToCreateAccount()
            NavigationAction.Home -> navHostController.navigateToHome()
            NavigationAction.Intro -> navHostController.navigateToIntro()
            is NavigationAction.CreateTransaction ->
                navHostController.navigateToCreateTransaction(navigationState.accountId)

            is NavigationAction.EditAccount ->
                navHostController.navigateToEditAccount(navigationState.accountId)

            is NavigationAction.EditTransaction ->
                navHostController.navigateToEditTransaction(navigationState.transactionId)

            is NavigationAction.ReadAccount ->
                navHostController.navigateToAccountDetails(navigationState.accountId)

            is NavigationAction.ReadTransaction ->
                navHostController.navigateToTransactionDetails(navigationState.transactionId)
        }
    })

    val bottomSheetState = mainActivityState.bottomSheetAction.collectAsState().value
    val sheetState: SheetState = rememberModalBottomSheetState(true)
    LaunchedEffect(key1 = bottomSheetState, block = {
        if (bottomSheetState !is BottomSheetAction.Empty) {
            this.launch {
                when (bottomSheetState.isOpen) {
                    true -> sheetState.show()
                    false -> sheetState.hide()
                }
            }.invokeOnCompletion {
                if (!bottomSheetState.isOpen) mainActivityState.resetBottomSheet()
            }
        }
    })
    when (bottomSheetState) {
        BottomSheetAction.Empty -> {}
        else -> {
            ModalBottomSheet(
                modifier = Modifier.padding(horizontal = 4.dp),
                onDismissRequest = {
                    mainActivityState.closeBottomSheet()
                },
                sheetState = sheetState,
                content = {
                    Column(
                        modifier = Modifier.padding(6.dp),
                        content = {
                            when (bottomSheetState) {
                                BottomSheetAction.Empty -> {}
                                is BottomSheetAction.SearchForAccount -> {}
                                is BottomSheetAction.Profile -> BottomSheetProfile(
                                    email = "EMAIL",
                                    logout = {}
                                )
                            }
                        }
                    )
                }
            )
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AmAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    appBarAction = appBarState,
                    onClickProfile = mainActivityState::showProfileBottomSheet,
                    onSearchKeyChange = {},
                )
            },
            floatingActionButton = {
                maAppState.currentMainDestination?.addButton?.let { addButton ->
                    AmCustomBottomBarWithFab(
                        modifier = Modifier,
                        fabIcon = addButton.icon,
                        onClickFab = {
                            currentDestination?.route?.let {
                                maAppState.navigateToAddByCurrentNavigation(
                                    it
                                )
                            }
                        },
                        bottomBarItems = {
                            maAppState.mainDestination.forEach { destination ->
                                AmNavigationCustomItem(
                                    modifier = Modifier,
                                    isSelected = destination == maAppState.currentMainDestination,
                                    selectedIcon = destination.selectedAmIconsType,
                                    unSelectedIcon = destination.unSelectedAmIconsType,
                                    onSelect = { maAppState.navigateToMainDestination(destination) }
                                )
                            }
                        }
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
        ) { padding ->
            AmNavHost(
                modifier = Modifier.padding(padding),
                navHostController = maAppState.navHostController,
                sendMainAction = mainActivityState::updateMainState
            )
        }
    }

}