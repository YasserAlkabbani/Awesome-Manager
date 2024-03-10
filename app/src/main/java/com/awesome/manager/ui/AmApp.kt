package com.awesome.manager.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.awesome.manager.core.designsystem.component.AmExtendedFloatingActionButton
import com.awesome.manager.core.designsystem.component.AmNavigationBar
import com.awesome.manager.core.designsystem.component.AmNavigationItem
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.MainDestination
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

    val navigationState = mainActivityState.navigationState.collectAsState().value
    val appBarState = mainActivityState.appBarState.collectAsState().value
    val bottomSheetState = mainActivityState.bottomSheetState.collectAsState().value

    val currentDestination = maAppState.currentDestination
    val currentMainDestination = maAppState.currentMainDestination


    LaunchedEffect(key1 = loginState, key2 = currentDestination, block = {
        currentDestination?.route?.let {
            maAppState.navigateByAuthState(loginState, it)
        }
    })


    val sheetState: SheetState = rememberModalBottomSheetState(true)
    Timber.d("TEST_BOTTOM_SHEET ${bottomSheetState} ${bottomSheetState.isOpen} ${sheetState.currentValue}")
    LaunchedEffect(key1 = bottomSheetState, block = {
        this.launch {
            when (bottomSheetState) {
                BottomSheetAction.Empty -> sheetState.hide()
                is BottomSheetAction.SearchForAccount -> when(bottomSheetState.isOpen){
                    true -> sheetState.show()
                    false -> sheetState.hide()
                }
            }
        }.invokeOnCompletion {
            if (!sheetState.isVisible) mainActivityState.resetBottomSheet()
        }

    })
    when(bottomSheetState){
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
                    appBarAction = appBarState
                )
            },
            bottomBar = {
                if (maAppState.shouldShowBottomBar) {
                    MaBottomBar(
                        modifier = Modifier.navigationBarsPadding(),
                        mainDestinations = maAppState.mainDestination,
                        onNavigationToDestination = maAppState::navigateToMainDestination,
                        selectedMainDestination = maAppState.currentMainDestination
                    )
                }
            },
            floatingActionButton = {
                maAppState.currentMainDestination?.addButton?.let { addButton ->
                    AmExtendedFloatingActionButton(
                        modifier = Modifier,
                        expanded = true,
                        text = addButton.title.asText(),
                        icon = addButton.icon,
                        onClick = {
                            currentDestination?.route?.let {
                                maAppState.navigateToAddByCurrentNavigation(
                                    it
                                )
                            }
                        }
                    )
                }
            },
        ) { padding ->
            AmNavHost(
                modifier = Modifier.padding(padding),
                navHostController = maAppState.navHostController,
                sendMainAction = mainActivityState::sendMainAction
            )
        }
    }
}


@Composable
fun MaBottomBar(
    modifier: Modifier,
    mainDestinations: List<MainDestination>,
    onNavigationToDestination: (MainDestination) -> Unit,
    selectedMainDestination: MainDestination?
) {
    AmNavigationBar(
        modifier = modifier,
        content = {
            mainDestinations.forEach { destination ->
                AmNavigationItem(
                    modifier = Modifier,
                    isSelected = destination == selectedMainDestination,
                    selectedIcon = destination.selectedAmIconsType,
                    unSelectedIcon = destination.unSelectedAmIconsType,
                    label = destination.title.asText(),
                    onSelect = { onNavigationToDestination(destination) }
                )
            }
        }
    )
}